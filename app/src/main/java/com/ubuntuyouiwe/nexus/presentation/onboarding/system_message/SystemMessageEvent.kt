package com.ubuntuyouiwe.nexus.presentation.onboarding.system_message

import com.ubuntuyouiwe.nexus.presentation.onboarding.user_name.UserNameEvent

sealed interface SystemMessageEvent {
    data class UserNameEnter(val systemMessage: String): SystemMessageEvent

    data object UpdateSystemMessage: SystemMessageEvent
}