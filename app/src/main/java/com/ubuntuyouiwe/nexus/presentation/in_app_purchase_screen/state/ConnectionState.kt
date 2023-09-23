package com.ubuntuyouiwe.nexus.presentation.in_app_purchase_screen.state

data class ConnectionState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
