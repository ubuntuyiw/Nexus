package com.ubuntuyouiwe.nexus.presentation.login.auth_choice

import android.content.Intent
import androidx.navigation.NavController

sealed interface AuthChoiceEvent {
    data class GoogleSignIn(val intent: Intent) : AuthChoiceEvent
    data class Email(val navController: NavController) : AuthChoiceEvent

    data class PrivacyPolicy(val onNavigate: (String) -> Unit) : AuthChoiceEvent

    data class ChangeTheme(val themeOrdinal: Int): AuthChoiceEvent

}