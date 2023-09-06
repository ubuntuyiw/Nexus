package com.ubuntuyouiwe.nexus.data.source.local.proto

import com.ubuntuyouiwe.nexus.ChatRoomFilterDto
import com.ubuntuyouiwe.nexus.ChatRoomShortDto
import com.ubuntuyouiwe.nexus.SettingsDto
import kotlinx.coroutines.flow.Flow

interface ProtoDataStoreDataSource {

    suspend fun updateSettings(updateAction: (SettingsDto.Builder) -> Unit): SettingsDto

    fun getSettings(): Flow<SettingsDto>


    fun getChatRoomFilter(): Flow<ChatRoomFilterDto>
    suspend fun updateChatRoomFilter(updateAction: (ChatRoomFilterDto.Builder) -> Unit): ChatRoomFilterDto

    suspend fun updateChatRoomShort(updateAction: (ChatRoomShortDto.Builder) -> Unit): ChatRoomShortDto
    fun getChatRoomShort(): Flow<ChatRoomShortDto>

}