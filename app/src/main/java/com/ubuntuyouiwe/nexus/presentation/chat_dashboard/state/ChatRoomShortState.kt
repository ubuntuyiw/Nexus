package com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state

import com.ubuntuyouiwe.nexus.domain.model.ChatRoomShort
import com.ubuntuyouiwe.nexus.domain.model.Settings

data class ChatRoomShortState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val data: ChatRoomShort = ChatRoomShort(),
)