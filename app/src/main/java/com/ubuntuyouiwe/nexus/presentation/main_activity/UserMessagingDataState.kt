package com.ubuntuyouiwe.nexus.presentation.main_activity

import com.ubuntuyouiwe.nexus.domain.model.user_messaging_data.UserMessagingData

data class UserMessagingDataState(
    val isSuccess: Boolean = false,
    val successData: UserMessagingData? = UserMessagingData(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
