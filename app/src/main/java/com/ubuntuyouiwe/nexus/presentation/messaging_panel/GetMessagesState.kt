package com.ubuntuyouiwe.nexus.presentation.messaging_panel

import com.ubuntuyouiwe.nexus.domain.model.messages.Message

data class GetMessagesState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val data: Message = Message(emptyList()),
)
