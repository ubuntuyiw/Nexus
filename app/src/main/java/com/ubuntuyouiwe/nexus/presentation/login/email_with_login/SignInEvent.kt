package com.ubuntuyouiwe.nexus.presentation.login.email_with_login

import com.ubuntuyouiwe.nexus.presentation.navigation.Screen

sealed interface SignInEvent {

    data class EnterEmail(val emailText: String): SignInEvent
    data class EnterPassword(val passwordText: String): SignInEvent
    object ChangePasswordVisibility: SignInEvent

    data class ChangeDialogVisibility(val isVisibility: Boolean): SignInEvent

    data class NavController(val navController: (Screen) -> Unit): SignInEvent

    object SignIn : SignInEvent
    object Send : SignInEvent

}