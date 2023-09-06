package com.ubuntuyouiwe.nexus.domain.repository

import com.ubuntuyouiwe.nexus.ChatRoomShortDto
import com.ubuntuyouiwe.nexus.domain.model.ChatRoomShort
import kotlinx.coroutines.flow.Flow

interface ChatRoomShortRepository {

    suspend fun updateChatRoomShort(updateAction: (ChatRoomShortDto.Builder) -> Unit): ChatRoomShort

    fun getChatRoomShort(): Flow<ChatRoomShort>
}