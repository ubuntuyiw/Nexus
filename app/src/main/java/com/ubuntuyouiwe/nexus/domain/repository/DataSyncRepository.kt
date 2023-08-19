package com.ubuntuyouiwe.nexus.domain.repository

import com.ubuntuyouiwe.nexus.domain.model.ChatRoom
import com.ubuntuyouiwe.nexus.domain.model.ChatRooms
import com.ubuntuyouiwe.nexus.domain.model.roles.Role
import com.ubuntuyouiwe.nexus.domain.model.messages.Message
import com.ubuntuyouiwe.nexus.domain.model.messages.MessageItem
import com.ubuntuyouiwe.nexus.domain.model.messages.Messages
import kotlinx.coroutines.flow.Flow

interface DataSyncRepository {
    suspend fun getChatRooms(): Flow<ChatRooms>

    suspend fun sendMessage(chatRoom: ChatRoom, messages: List<MessageItem>)
    suspend fun getChatRoom(id: String): Flow<ChatRoom?>
    suspend fun getChatRoomMessage(id: String): Flow<Message?>

}