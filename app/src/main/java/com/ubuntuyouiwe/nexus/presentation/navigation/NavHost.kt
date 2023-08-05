package com.ubuntuyouiwe.nexus.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.ChatDashBoard
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.ChatDashBoardViewModel
import com.ubuntuyouiwe.nexus.presentation.login.auth_choice.AuthenticationChoiceScreen
import com.ubuntuyouiwe.nexus.presentation.login.auth_choice.AuthenticationChoiceViewModel
import com.ubuntuyouiwe.nexus.presentation.login.email_with_login.EmailWithLogInViewModel
import com.ubuntuyouiwe.nexus.presentation.login.email_with_login.EmailWithLoginScreen
import com.ubuntuyouiwe.nexus.presentation.login.email_with_signup.EmailWithSignUp
import com.ubuntuyouiwe.nexus.presentation.login.email_with_signup.EmailWithSignUpViewModel
import com.ubuntuyouiwe.nexus.presentation.main_activity.widgets.Splash
import com.ubuntuyouiwe.nexus.presentation.messaging_panel.MessagingPanelScreen

@Composable
fun NavHostScreen(startDestination: Screen) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination.name) {
        composable(Screen.SPLASH.name) {
            Splash()
        }
        composable(Screen.AUTHENTICATION_CHOICE.name) {
            val viewModel: AuthenticationChoiceViewModel = hiltViewModel()
            val googleSignInState by viewModel.googleSignInState
            val googleSignInButtonState by viewModel.googleSignInButtonState

            AuthenticationChoiceScreen(
                navController,
                googleSignInState,
                googleSignInButtonState,
                viewModel::googleSignInCheckAndStart,
                viewModel::onEvent

            )
        }

        composable(Screen.EMAIL_WITH_LOGIN.name) {
            val viewModel: EmailWithLogInViewModel = hiltViewModel()
            val signInState by viewModel.signInState
            val emailState by viewModel.emailState
            val passwordState by viewModel.passwordState
            val signInButtonState by viewModel.signInButtonState
            val passwordResetState by viewModel.passwordResetState
            EmailWithLoginScreen(
                navController,
                signInState,
                emailState,
                passwordState,
                passwordResetState,
                signInButtonState,
                viewModel::onEvent

            )
        }

        composable(Screen.EMAIL_WITH_SIGNUP.name) {
            val viewModel: EmailWithSignUpViewModel = hiltViewModel()
            val emailState by viewModel.emailState
            val passwordState by viewModel.passwordState
            val signUpState by viewModel.signUpState
            val signUpButtonState by viewModel.signUpButtonState

            EmailWithSignUp(
                navController,
                signUpState,
                emailState,
                passwordState,
                signUpButtonState,
                viewModel::onEvent
            )
        }

        composable(Screen.CHAT_DASHBOARD.name) {
            val viewModel: ChatDashBoardViewModel = hiltViewModel()
            val stateLogOut by viewModel.stateLogOut
            val createChatRoomState by viewModel.createChatRoomState
            val dialogProperties = viewModel.dialogProperties
            ChatDashBoard(stateLogOut, createChatRoomState, dialogProperties, viewModel::onEvent)
        }

        composable(Screen.MessagingPanel.name) {
            MessagingPanelScreen()
        }

    }

}