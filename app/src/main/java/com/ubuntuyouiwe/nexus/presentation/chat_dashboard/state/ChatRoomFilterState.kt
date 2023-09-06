package com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state

import com.ubuntuyouiwe.nexus.domain.model.ChatRoomFilter
import com.ubuntuyouiwe.nexus.domain.model.Settings

data class ChatRoomFilterState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val data: ChatRoomFilter = ChatRoomFilter(),
)