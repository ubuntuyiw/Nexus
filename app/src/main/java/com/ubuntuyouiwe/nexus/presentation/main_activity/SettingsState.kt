package com.ubuntuyouiwe.nexus.presentation.main_activity

import com.ubuntuyouiwe.nexus.domain.model.Settings
import com.ubuntuyouiwe.nexus.domain.model.User

data class SettingsState(
    val isSuccess: Boolean = false,
    val successData: Settings = Settings(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
