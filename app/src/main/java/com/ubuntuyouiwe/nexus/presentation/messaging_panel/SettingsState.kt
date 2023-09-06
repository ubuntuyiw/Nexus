package com.ubuntuyouiwe.nexus.presentation.messaging_panel

import com.ubuntuyouiwe.nexus.domain.model.Settings

data class SettingsState(
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val data: Settings = Settings()
)
