package com.ubuntuyouiwe.nexus.data.repository

import com.ubuntuyouiwe.nexus.ChatRoomShortDto
import com.ubuntuyouiwe.nexus.data.source.local.proto.ProtoDataStoreDataSource
import com.ubuntuyouiwe.nexus.data.util.toChatRoomShort
import com.ubuntuyouiwe.nexus.domain.model.ChatRoomShort
import com.ubuntuyouiwe.nexus.domain.repository.ChatRoomShortRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatRoomShortRepositoryImpl @Inject constructor(
    private val protoDataStoreDataSource: ProtoDataStoreDataSource

): ChatRoomShortRepository {
    override suspend fun updateChatRoomShort(updateAction: (ChatRoomShortDto.Builder) -> Unit): ChatRoomShort {
        return protoDataStoreDataSource.updateChatRoomShort(updateAction).toChatRoomShort()
    }

    override fun getChatRoomShort(): Flow<ChatRoomShort> = protoDataStoreDataSource.getChatRoomShort().map { it.toChatRoomShort() }
}