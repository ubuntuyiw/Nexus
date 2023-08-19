package com.ubuntuyouiwe.nexus.presentation.login.email_with_login.state

data class ResetPasswordState(
    var isSuccess: Boolean = false,
    var isLoading: Boolean = false,
    var isErrorState: Boolean = false,
    var errorMessage: String = "",
    var dialogVisibility: Boolean = false
    )