package com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state

import com.ubuntuyouiwe.nexus.domain.model.ChatRoomFilter
import java.util.UUID

data class ChatRoomDeleteState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val data: UUID = UUID.randomUUID()
)
