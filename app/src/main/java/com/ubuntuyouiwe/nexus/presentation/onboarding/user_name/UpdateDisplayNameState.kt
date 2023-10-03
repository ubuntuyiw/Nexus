package com.ubuntuyouiwe.nexus.presentation.onboarding.user_name

data class UpdateDisplayNameState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
)
