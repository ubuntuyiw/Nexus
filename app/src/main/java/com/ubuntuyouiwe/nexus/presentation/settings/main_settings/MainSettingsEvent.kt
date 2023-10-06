package com.ubuntuyouiwe.nexus.presentation.settings.main_settings

sealed interface MainSettingsEvent {
    data class ChangeMainSettings(val themeOrdinal: Int): MainSettingsEvent
}