package com.ubuntuyouiwe.nexus.presentation.create_chat_room

import com.ubuntuyouiwe.nexus.domain.model.ChatRoom

data class ChatRoomsState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val isFromCache: Boolean = false,
    val data: List<ChatRoom> = emptyList()
)
