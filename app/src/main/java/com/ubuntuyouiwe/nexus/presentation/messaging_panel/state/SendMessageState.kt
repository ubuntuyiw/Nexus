package com.ubuntuyouiwe.nexus.presentation.messaging_panel.state

data class SendMessageState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
