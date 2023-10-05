package com.ubuntuyouiwe.nexus.presentation.settings.theme

sealed interface ThemeEvent {
    data class ChangeTheme(val themeOrdinal: Int): ThemeEvent
}