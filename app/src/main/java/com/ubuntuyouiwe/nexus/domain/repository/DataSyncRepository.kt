package com.ubuntuyouiwe.nexus.domain.repository

import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.ChatRooms
import com.ubuntuyouiwe.nexus.domain.model.TermsOfUseModel
import com.ubuntuyouiwe.nexus.domain.model.messages.Message
import com.ubuntuyouiwe.nexus.domain.model.messages.MessageItem
import kotlinx.coroutines.flow.Flow

interface DataSyncRepository {
    suspend fun getChatRooms(): Flow<ChatRooms>
    fun getTermsOfUse(): TermsOfUseModel
    suspend fun sendMessage(chatRoom: ChatRoom, messages: List<MessageItem>,   isUserMessagingData: Boolean)
    suspend fun getChatRoom(id: String): Flow<ChatRoom?>
    suspend fun getChatRoomMessage(id: String): Flow<Message?>

    suspend fun updateChatRoomDocuments(chatRooms: List<ChatRoom>)

    suspend fun deleteChatRoomDocuments(chatRooms: List<ChatRoom>)



}