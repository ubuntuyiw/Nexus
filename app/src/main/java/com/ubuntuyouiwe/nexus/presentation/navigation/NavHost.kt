package com.ubuntuyouiwe.nexus.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ubuntuyouiwe.nexus.presentation.login.auth_choice.AuthenticationChoiceScreen
import com.ubuntuyouiwe.nexus.presentation.login.email_with_login.EmailWithLoginScreen
import com.ubuntuyouiwe.nexus.presentation.login.email_with_signup.EmailWithSignUp

@Composable
fun NavHostScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.AUTHENTICATION_CHOICE.name) {

        composable(Screen.AUTHENTICATION_CHOICE.name) {
            AuthenticationChoiceScreen(navController)
        }

        composable(Screen.EMAIL_WITH_LOGIN.name) {
            EmailWithLoginScreen(navController)
        }

        composable(Screen.EMAIL_WITH_SIGNUP.name) {
            EmailWithSignUp(navController)
        }

        composable(Screen.CHAT_DASHBOARD.name) {

        }

    }

}