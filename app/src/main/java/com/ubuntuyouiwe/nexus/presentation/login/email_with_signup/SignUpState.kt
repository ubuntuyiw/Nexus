package com.ubuntuyouiwe.nexus.presentation.login.email_with_signup

data class SignUpState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
