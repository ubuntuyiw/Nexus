package com.ubuntuyouiwe.nexus.presentation.main_activity

data class SaveTokenState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
