package com.ubuntuyouiwe.nexus.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
import com.ubuntuyouiwe.nexus.presentation.messaging_panel.MessagingPanelViewModel

@Composable
fun NavHostScreen(startDestination: Screen) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination.name) {
        composable(Screen.SPLASH.name) {
            Splash()
        }
        composable(
            route = Screen.AUTHENTICATION_CHOICE.name,
            enterTransition = null,
            exitTransition = null,
            popEnterTransition = null,
            popExitTransition = null,) {


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

        composable(
            route = Screen.EMAIL_WITH_LOGIN.name,
            enterTransition = null,
            exitTransition = null,
            popEnterTransition = null,
            popExitTransition = null,
        ) {
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

        composable(
            route = Screen.EMAIL_WITH_SIGNUP.name,
            enterTransition = null,
            exitTransition = null,
            popEnterTransition = null,
            popExitTransition = null,) {
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

        composable(
            route = Screen.CHAT_DASHBOARD.name,
            enterTransition = null,
            exitTransition = null,
            popEnterTransition = null,
            popExitTransition = null,
        ) {

            val viewModel: ChatDashBoardViewModel = hiltViewModel()
            val stateLogOut by viewModel.stateLogOut
            val createChatRoomState by viewModel.createChatRoomState
            val dialogState = viewModel.dialogProperties
            val menuState by viewModel.menuState
            val chatRoomState by viewModel.chatRoomsState
            val roles by viewModel.rolesState

            ChatDashBoard(
                navController,
                stateLogOut,
                createChatRoomState,
                dialogState,
                menuState,
                roles,
                chatRoomState,
                viewModel::onEvent
            )
        }

        composable(
            route = Screen.MessagingPanel.name+"/{role}/{id}",
            enterTransition = null,
            exitTransition = null,
            popEnterTransition = null,
            popExitTransition = null,
            arguments = listOf(
                navArgument("role") {
                    this.nullable = true
                    type = NavType.StringType
                },
                navArgument("id") {
                    this.nullable = true
                    type = NavType.StringType
                }

                )) {
            val viewModel: MessagingPanelViewModel = hiltViewModel()
            val role by viewModel.rolesState
            val getMessagesState by viewModel.getMessagesState
            val messageTextFieldState by viewModel.messageTextFieldState
            val sendMessageButtonState by viewModel.sendMessageButtonState
            val chatRoomState by viewModel.chatRoomState
            val speakState by viewModel.speechState

            MessagingPanelScreen(navController, role, messageTextFieldState, sendMessageButtonState, getMessagesState, chatRoomState, speakState, viewModel::onEvent)
        }

    }

}