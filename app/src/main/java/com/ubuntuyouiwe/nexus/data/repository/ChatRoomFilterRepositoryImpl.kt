package com.ubuntuyouiwe.nexus.data.repository

import com.ubuntuyouiwe.nexus.ChatRoomFilterDto
import com.ubuntuyouiwe.nexus.data.source.local.proto.ProtoDataStoreDataSource
import com.ubuntuyouiwe.nexus.data.util.toChatRoomFilter
import com.ubuntuyouiwe.nexus.domain.model.ChatRoomFilter
import com.ubuntuyouiwe.nexus.domain.repository.ChatRoomFilterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatRoomFilterRepositoryImpl @Inject constructor(
    private val protoDataStoreDataSource: ProtoDataStoreDataSource
) : ChatRoomFilterRepository {

    override suspend fun updateChatRoomFilter(updateAction: (ChatRoomFilterDto.Builder) -> Unit): ChatRoomFilter {
        return protoDataStoreDataSource.updateChatRoomFilter(updateAction).toChatRoomFilter()
    }

    override fun getChatRoomFilter(): Flow<ChatRoomFilter> =
        protoDataStoreDataSource.getChatRoomFilter().map { it.toChatRoomFilter() }

}