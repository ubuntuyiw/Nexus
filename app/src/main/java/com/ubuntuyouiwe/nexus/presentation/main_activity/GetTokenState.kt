package com.ubuntuyouiwe.nexus.presentation.main_activity

import com.ubuntuyouiwe.nexus.domain.model.User

data class GetTokenState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val successData: String = "",
    val errorMessage: String = ""
)
