package com.ubuntuyouiwe.nexus.presentation.login.email_with_login

import com.ubuntuyouiwe.nexus.presentation.navigation.Screen

sealed interface SignInEvent {

    data class EnterEmail(val emailText: String): SignInEvent
    data class EnterPassword(val passwordText: String): SignInEvent
    data object ChangePasswordVisibility: SignInEvent

    data class ChangeDialogVisibility(val isVisibility: Boolean): SignInEvent

    data class NavController(val navController: (Screen) -> Unit): SignInEvent

    data object SignIn : SignInEvent
    data object Send : SignInEvent

}