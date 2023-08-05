package com.ubuntuyouiwe.nexus.presentation.login.email_with_signup

sealed interface SignUpEvent {

    data class EnterEmail(val value: String): SignUpEvent

    data class EnterPassword(val value: String): SignUpEvent

    object ChangePasswordVisibility: SignUpEvent

    object SignUp : SignUpEvent

}

