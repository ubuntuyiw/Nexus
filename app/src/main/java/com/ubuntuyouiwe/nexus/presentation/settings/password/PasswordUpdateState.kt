package com.ubuntuyouiwe.nexus.presentation.settings.password

data class PasswordUpdateState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
