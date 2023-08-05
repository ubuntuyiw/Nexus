package com.ubuntuyouiwe.nexus.presentation.login.email_with_login

data class SignInState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = ""

)
