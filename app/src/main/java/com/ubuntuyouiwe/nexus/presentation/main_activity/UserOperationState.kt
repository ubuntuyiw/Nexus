package com.ubuntuyouiwe.nexus.presentation.main_activity

import com.ubuntuyouiwe.nexus.domain.model.User

data class UserOperationState(
    val isSuccess: Boolean = false,
    val successData: User? = User(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
