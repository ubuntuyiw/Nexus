package com.ubuntuyouiwe.nexus.presentation.chat_dashboard.state

data class SignOutState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
)