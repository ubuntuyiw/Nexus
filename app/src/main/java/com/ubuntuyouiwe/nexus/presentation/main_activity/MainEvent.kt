package com.ubuntuyouiwe.nexus.presentation.main_activity

import com.ubuntuyouiwe.nexus.presentation.settings.theme.ThemeCategory

sealed interface MainEvent {
    data object Retry: MainEvent
}