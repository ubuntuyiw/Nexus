package com.ubuntuyouiwe.nexus.data.repository

import android.content.Context
import android.util.Log
import com.ubuntuyouiwe.nexus.data.dto.AIRequest
import com.ubuntuyouiwe.nexus.data.dto.AIRequestBody
import com.ubuntuyouiwe.nexus.data.dto.ChatRoomDto
import com.ubuntuyouiwe.nexus.data.dto.TermsOfUseDto
import com.ubuntuyouiwe.nexus.data.dto.messages.MessageDto
import com.ubuntuyouiwe.nexus.data.dto.messages.MessageItemDto
import com.ubuntuyouiwe.nexus.data.dto.messages.MessagesDto
import com.ubuntuyouiwe.nexus.data.dto.roles.RoleDto
import com.ubuntuyouiwe.nexus.data.dto.user.UserDto
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.nexus.data.util.FirebaseCollections
import com.ubuntuyouiwe.nexus.data.util.dto_type.messages.MessagesFields
import com.ubuntuyouiwe.nexus.data.util.firstToHashMap
import com.ubuntuyouiwe.nexus.data.util.toChatRoom
import com.ubuntuyouiwe.nexus.data.util.toHashMap
import com.ubuntuyouiwe.nexus.data.util.toMessage
import com.ubuntuyouiwe.nexus.data.util.toTermsOfUseModel
import com.ubuntuyouiwe.nexus.di.SystemMessages
import com.ubuntuyouiwe.nexus.di.TermsOfUse
import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.ChatRooms
import com.ubuntuyouiwe.nexus.domain.model.TermsOfUseModel
import com.ubuntuyouiwe.nexus.domain.model.messages.Message
import com.ubuntuyouiwe.nexus.domain.model.messages.MessageItem
import com.ubuntuyouiwe.nexus.domain.repository.DataSyncRepository
import com.ubuntuyouiwe.nexus.domain.util.toChatRoomDto
import com.ubuntuyouiwe.nexus.domain.util.toMessageItemDto
import com.ubuntuyouiwe.nexus.domain.util.toRolesDto
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DataSyncRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
    @ApplicationContext val context: Context,
    @SystemMessages private val systemMessages: List<MessageItemDto>,
    @TermsOfUse private val termsOfUseDto: List<TermsOfUseDto>
) : DataSyncRepository {

    override fun getTermsOfUse(): TermsOfUseModel {
        return termsOfUseDto.map { it.toTermsOfUseModel() }.first()
    }


    override suspend fun getChatRoom(id: String): Flow<ChatRoom?> {
        return firebaseDataSource.getDocumentListener(FirebaseCollections.ChatRooms, id).map {
            if (it.isSuccess) {
                it.getOrNull()?.documents?.firstOrNull()?.toObject(ChatRoomDto::class.java)
                    ?.toChatRoom()

            } else {
                throw it.exceptionOrNull()!!
            }
        }
    }


    override suspend fun sendMessage(chatRoom: ChatRoom, messages: List<MessageItem>) {
        if (chatRoom.isNew) {
            sendInitialMessage(chatRoom, messages)
        } else {
            sendSubsequentMessage(chatRoom, messages)
        }
    }

    override suspend fun updateChatRoomDocuments(chatRooms: List<ChatRoom>) {
        val chatRoomToDto = chatRooms.map { it.toChatRoomDto() }
        val listHashMap = chatRoomToDto.map { hashMapOf(it.id to it.toHashMap()) }

        val finalHashMap = HashMap<String, HashMap<String, Any?>>()
        for (singleHashMap in listHashMap) {
            for ((key, value) in singleHashMap) {
                finalHashMap[key!!] = value
            }
        }

        firebaseDataSource.batchSet(FirebaseCollections.ChatRooms, finalHashMap)
    }

    override suspend fun deleteChatRoomDocuments(chatRooms: List<ChatRoom>) {
        val id = chatRooms.map { it.id }
        firebaseDataSource.batchDeleteWithSubCollections(FirebaseCollections.ChatRooms, id)
    }


    private suspend fun sendInitialMessage(chatRoom: ChatRoom, messages: List<MessageItem>) {
        val rolesDto = chatRoom.role.toRolesDto()
        val messagesItemToDto = messages.map { it.toMessageItemDto() }
        val chatRoomToDto = chatRoom.toChatRoomDto()
            .copy(ownerId = firebaseDataSource.userState()?.uid)

        firebaseDataSource.set(
            FirebaseCollections.ChatRooms,
            chatRoomToDto.id!!,
            chatRoomToDto.firstToHashMap()
        )
        val messageResult = firebaseDataSource.addSubCollection(
            FirebaseCollections.ChatRooms,
            chatRoom.id,
            FirebaseCollections.Messages,
            MessagesDto(messages = messagesItemToDto).toHashMap()
        )
        messageResult.update(
            mapOf(
                MessagesFields.ID.key to messageResult.id
            )
        ).await()

        ai(messageResult.id, chatRoomToDto.id, rolesDto.system!!, messagesItemToDto)
    }

    private suspend fun ai(
        messageId: String,
        chatroomId: String,
        system: String,
        messages: List<MessageItemDto>
    ) {
        val uid = firebaseDataSource.userState()?.uid.orEmpty()
        val userInfo = firebaseDataSource.getDocument(FirebaseCollections.Users, uid)
            .toObjects(UserDto::class.java).first()
        val userInfoName =
            MessageItemDto(role = "system", content = "User Name: " + userInfo.displayName)
        val userInfoSystemMessage =
            MessageItemDto(role = "system", content = "User Info: " + userInfo.systemMessage)
        val systemMessageRole = MessageItemDto(role = "system", content = system)
        val aiRequestBody = AIRequestBody(
            messages = this.systemMessages + listOf(
                systemMessageRole,
                userInfoSystemMessage,
                userInfoName
            ) + messages
        )

        val aiRequest = AIRequest(
            aiRequestBody = aiRequestBody,
            chatRoomId = chatroomId,
            info = listOf(systemMessageRole),
            messageId = messageId,
            ownerId = firebaseDataSource.userState()?.uid ?: return
        )
        firebaseDataSource.ai(aiRequest)
    }


    private suspend fun sendSubsequentMessage(chatRoom: ChatRoom, messages: List<MessageItem>) {

        val rolesDto = chatRoom.role.toRolesDto()
        val messagesItemToDto = messages.map { it.toMessageItemDto() }

        val chatRoomToDto =
            chatRoom.toChatRoomDto().copy(ownerId = firebaseDataSource.userState()?.uid)
        val messageResult = firebaseDataSource.addSubCollection(
            FirebaseCollections.ChatRooms,
            chatRoom.id,
            FirebaseCollections.Messages,
            MessagesDto(messages = listOf(messagesItemToDto.last())).toHashMap()
        )
        messageResult.update(
            mapOf(
                MessagesFields.ID.key to messageResult.id
            )
        ).await()
        firebaseDataSource.set(
            FirebaseCollections.ChatRooms,
            chatRoomToDto.id!!,
            chatRoomToDto.toHashMap()
        )
        ai(messageResult.id, chatRoomToDto.id, rolesDto.system!!, messagesItemToDto)
    }


    override suspend fun getChatRooms(): Flow<ChatRooms> {
        return firebaseDataSource.getAllDocumentListener(FirebaseCollections.ChatRooms)
            .map { result ->
                if (result.isSuccess) {
                    val data = result.getOrNull()?.metadata?.isFromCache?.let { isFromCache ->
                        ChatRooms(
                            messageResult = result.getOrNull()?.let { querySnapshot ->
                                querySnapshot.documents.map { documentSnapshot ->


                                    documentSnapshot.toObject(ChatRoomDto::class.java)?.toChatRoom()
                                        ?: ChatRoomDto(role = RoleDto()).toChatRoom()
                                }
                            } ?: emptyList(),
                            isFromCache = isFromCache

                        )
                    }
                    ChatRooms(
                        messageResult = data?.messageResult ?: emptyList(),
                        isFromCache = data?.isFromCache ?: false

                    )
                } else {
                    throw result.exceptionOrNull()!!
                }
            }
    }


    override suspend fun getChatRoomMessage(id: String): Flow<Message?> {
        return firebaseDataSource.getAllSubCollectionDocumentListener(
            FirebaseCollections.ChatRooms,
            id
        ).map {
            if (it.isSuccess) {
                MessageDto(
                    it.getOrNull()?.documents?.mapNotNull { documentSnapshot ->

                        var messageResult = documentSnapshot.toObject(MessagesDto::class.java)
                        messageResult = messageResult?.copy(
                            hasPendingWrites = documentSnapshot.metadata.hasPendingWrites(),
                        )
                        messageResult

                    } ?: emptyList(),
                    isFromCache = it.getOrNull()?.metadata?.isFromCache ?: false

                ).toMessage()

            } else {
                throw it.exceptionOrNull()!!
            }
        }
    }


}