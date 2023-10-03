package com.ubuntuyouiwe.nexus.presentation.onboarding.user_name

sealed interface UserNameEvent {

    data class UserNameEnter(val userName: String): UserNameEvent

    data class UpdateUserName(val userName: String): UserNameEvent

    data object UpdateDisplayNameStateReset: UserNameEvent

}