package com.ubuntuyouiwe.nexus.presentation.chat_dashboard

data class SignOutState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val success: String = ""
)