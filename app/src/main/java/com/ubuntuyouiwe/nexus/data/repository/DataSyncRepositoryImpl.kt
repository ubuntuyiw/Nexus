package com.ubuntuyouiwe.nexus.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.ubuntuyouiwe.nexus.data.dto.AIRequest
import com.ubuntuyouiwe.nexus.data.dto.AIRequestBody
import com.ubuntuyouiwe.nexus.data.dto.ChatRoomDto
import com.ubuntuyouiwe.nexus.data.dto.MessageItemDto
import com.ubuntuyouiwe.nexus.data.dto.MessagesDto
import com.ubuntuyouiwe.nexus.data.dto.roles.RoleDto
import com.ubuntuyouiwe.nexus.data.source.remote.firebase.FirebaseDataSource
import com.ubuntuyouiwe.nexus.data.util.FirebaseCollections
import com.ubuntuyouiwe.nexus.data.util.firstToHashMap
import com.ubuntuyouiwe.nexus.data.util.toChatRoom
import com.ubuntuyouiwe.nexus.data.util.toHashMap
import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.ChatRooms
import com.ubuntuyouiwe.nexus.domain.model.messages.Message
import com.ubuntuyouiwe.nexus.domain.model.messages.MessageItem
import com.ubuntuyouiwe.nexus.domain.model.messages.Messages
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
) : DataSyncRepository {


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
            Toast.makeText(context, "ilk mesaj değil", Toast.LENGTH_LONG).show()
            sendSubsequentMessage(chatRoom, messages)
        }
    }

    private suspend fun sendInitialMessage(chatRoom: ChatRoom, messages: List<MessageItem>) {
        val rolesDto = chatRoom.role.toRolesDto()
        val messagesItemToDto = messages.map { it.toMessageItemDto() }
        val chatRoomToDto =
            chatRoom.toChatRoomDto().copy(ownerId = firebaseDataSource.userState()?.uid)
        Log.v("dawddwddd",messagesItemToDto.toString())

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
                "id" to messageResult.id
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
        val systemMessage = MessageItemDto(role = "system", content = system)
        val aiRequestBody = AIRequestBody(model = "gpt-3.5-turbo-16k-0613", messages = messages)

        aiRequestBody.messages.forEach {
            Log.v("wdwdwdw",it.toString())

        }

        val aiRequest = AIRequest(
            aiRequestBody = aiRequestBody,
            chatRoomId = chatroomId,
            info = listOf(systemMessage),
            messageId = messageId,
            ownerId = firebaseDataSource.userState()?.uid?:""
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
                "id" to messageResult.id
            )
        ).await()

        ai(messageResult.id, chatRoomToDto.id!!, rolesDto.system!!, messagesItemToDto)

        firebaseDataSource.set(
            FirebaseCollections.ChatRooms,
            chatRoomToDto.id,
            chatRoomToDto.toHashMap()
        )

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
                Message(
                    it.getOrNull()?.documents?.mapNotNull { documentSnapshot ->

                        var messageResult = documentSnapshot.toObject(Messages::class.java)
                        messageResult = messageResult?.copy(
                            hasPendingWrites = documentSnapshot.metadata.hasPendingWrites(),
                        )
                        messageResult

                    } ?: emptyList(),
                    isFromCache = it.getOrNull()?.metadata?.isFromCache ?: false

                )

            } else {
                throw it.exceptionOrNull()!!
            }
        }
    }


}