package com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state

data class ChatRoomUpdateState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val dialogVisibility: Boolean = false
)
