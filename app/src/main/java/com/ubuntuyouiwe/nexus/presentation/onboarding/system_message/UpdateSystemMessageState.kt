package com.ubuntuyouiwe.nexus.presentation.onboarding.system_message

data class UpdateSystemMessageState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
)
