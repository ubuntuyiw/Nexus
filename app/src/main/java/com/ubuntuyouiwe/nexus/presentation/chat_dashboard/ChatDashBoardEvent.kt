package com.ubuntuyouiwe.nexus.presentation.chat_dashboard

sealed interface ChatDashBoardEvent {

    object LogOut : ChatDashBoardEvent
    data class ChangeDialogVisibility(val isVisibility: Boolean): ChatDashBoardEvent

    data class ChangeMenuVisibility(val isVisibility: Boolean): ChatDashBoardEvent

}
