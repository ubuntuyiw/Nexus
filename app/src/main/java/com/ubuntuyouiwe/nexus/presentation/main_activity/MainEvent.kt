package com.ubuntuyouiwe.nexus.presentation.main_activity

sealed interface MainEvent {
    object Retry: MainEvent
}