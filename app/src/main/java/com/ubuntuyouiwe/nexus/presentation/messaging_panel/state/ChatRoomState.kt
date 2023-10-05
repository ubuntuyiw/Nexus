package com.ubuntuyouiwe.nexus.presentation.messaging_panel.state

import com.ubuntuyouiwe.nexus.domain.model.ChatRoom

data class ChatRoomState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val isFromCache: Boolean? = false,
    val data: ChatRoom = ChatRoom()
)
