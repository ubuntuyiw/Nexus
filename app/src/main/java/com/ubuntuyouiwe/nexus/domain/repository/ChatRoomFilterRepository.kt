package com.ubuntuyouiwe.nexus.domain.repository

import com.ubuntuyouiwe.nexus.ChatRoomFilterDto
import com.ubuntuyouiwe.nexus.domain.model.ChatRoomFilter
import kotlinx.coroutines.flow.Flow

interface ChatRoomFilterRepository {

    suspend fun updateChatRoomFilter(updateAction: (ChatRoomFilterDto.Builder) -> Unit): ChatRoomFilter
    fun getChatRoomFilter(): Flow<ChatRoomFilter>
}