package com.ubuntuyouiwe.nexus.presentation.login.auth_choice

import android.content.Intent
import androidx.navigation.NavController

sealed interface AuthChoiceEvent {
    class GoogleSignIn(val intent: Intent) : AuthChoiceEvent
    class Email(val navController: NavController) : AuthChoiceEvent

    class PrivacyPolicy(val onNavigate: (String) -> Unit) : AuthChoiceEvent

}