package com.ubuntuyouiwe.nexus.presentation.main_activity

sealed interface MainEvent {
    data object Retry: MainEvent

}