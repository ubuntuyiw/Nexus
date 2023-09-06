package com.ubuntuyouiwe.nexus.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.ChatDashBoard
import com.ubuntuyouiwe.nexus.presentation.chat_dashboard.ChatDashBoardViewModel
import com.ubuntuyouiwe.nexus.presentation.create_chat_room.CreateChatRoomScreen
import com.ubuntuyouiwe.nexus.presentation.create_chat_room.CreateChatRoomViewModel
import com.ubuntuyouiwe.nexus.presentation.login.auth_choice.AuthenticationChoiceScreen
import com.ubuntuyouiwe.nexus.presentation.login.auth_choice.AuthenticationChoiceViewModel
import com.ubuntuyouiwe.nexus.presentation.login.email_with_login.EmailWithLogInViewModel
import com.ubuntuyouiwe.nexus.presentation.login.email_with_login.EmailWithLoginScreen
import com.ubuntuyouiwe.nexus.presentation.login.email_with_signup.EmailWithSignUp
import com.ubuntuyouiwe.nexus.presentation.login.email_with_signup.EmailWithSignUpViewModel
import com.ubuntuyouiwe.nexus.presentation.main_activity.widgets.Splash
import com.ubuntuyouiwe.nexus.presentation.messaging_panel.MessagingPanelScreen
import com.ubuntuyouiwe.nexus.presentation.messaging_panel.MessagingPanelViewModel
import com.ubuntuyouiwe.nexus.presentation.photo_editing.PhotoEditingScreen
import com.ubuntuyouiwe.nexus.presentation.photo_editing.PhotoEditingViewModel

@Composable
fun NavHostScreen(startDestination: Screen) {
    val navController = rememberNavController()
    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels
    NavHost(navController = navController, startDestination = startDestination.name) {
        composable(Screen.SPLASH.name) {
            Splash()
        }
        composable(
            route = Screen.AUTHENTICATION_CHOICE.name,
            enterTransition = null,
            exitTransition = null,
            popEnterTransition = null,
            popExitTransition = null,
        ) { navBackStackEntry ->


            val viewModel: AuthenticationChoiceViewModel = hiltViewModel(navBackStackEntry)
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
        ) { navBackStackEntry ->
            val viewModel: EmailWithLogInViewModel = hiltViewModel(navBackStackEntry)
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
            popExitTransition = null,
        ) { navBackStackEntry ->
            val viewModel: EmailWithSignUpViewModel = hiltViewModel(navBackStackEntry)
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
        ) { navBackStackEntry ->

            val viewModel: ChatDashBoardViewModel = hiltViewModel(navBackStackEntry)
            val stateLogOut by viewModel.stateLogOut
            val chatRoomState by viewModel.chatRoomsState
            val filterState by viewModel.filter
            val chatRoomShort by viewModel.chatRoomShortState
            val chatRoomFilter by viewModel.chatRoomFilterState
            val chatRoomDeleteSate by viewModel.chatRoomDeleteSate
            val workManagerState by viewModel.workManagerState

            ChatDashBoard(
                navController,
                stateLogOut,
                chatRoomShort,
                chatRoomFilter,
                filterState,
                chatRoomState,
                chatRoomDeleteSate,
                workManagerState,
                viewModel::onEvent
            )
        }

        composable(
            route = Screen.MessagingPanel.name + "/{role}/{id}",
            arguments = listOf(
                navArgument("role") {
                    type = NavType.StringType
                },
                navArgument("id") {
                    this.nullable = true
                    type = NavType.StringType
                }

            ),
            enterTransition = {
                scaleIn() + fadeIn(initialAlpha = 0.3f)
            },
            exitTransition = {
                scaleOut() + fadeOut(targetAlpha = 0.3f)
            },
            popEnterTransition = {
                scaleIn() + fadeIn(initialAlpha = 0.3f)
            },
            popExitTransition = {
                scaleOut() + fadeOut(targetAlpha = 0.3f)
            }

        ) { navBackStackEntry ->
            val viewModel: MessagingPanelViewModel = hiltViewModel(navBackStackEntry)

            val role by viewModel.rolesState
            val getMessagesState by viewModel.getMessagesState
            val messageTextFieldState by viewModel.messageTextFieldState
            val sendMessageButtonState by viewModel.sendMessageButtonState
            val chatRoomState by viewModel.chatRoomState
            val sendMessageState by viewModel.sendMessageState
            val settingsState by viewModel.settingsState
            val photoUri = viewModel.photoUri

            MessagingPanelScreen(
                navController,
                role,
                sendMessageState,
                messageTextFieldState,
                sendMessageButtonState,
                getMessagesState,
                chatRoomState,
                photoUri,
                settingsState,
                viewModel::onEvent
            )
        }
        composable(
            Screen.PhotoEditingScreen.name + "/{photoUrl}",
            arguments = listOf(
                navArgument("photoUrl") {
                    nullable = true
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->

            val viewModel: PhotoEditingViewModel = hiltViewModel(navBackStackEntry)
            val croppedPhotoState by viewModel.croppedPhotoState
            val onEvent = viewModel::onEvent
            val bitmapToStringState by viewModel.bitmapToStringState
            val bitmap = viewModel.bitmap
            PhotoEditingScreen(navController, croppedPhotoState, bitmapToStringState, onEvent ,bitmap)
        }

        composable(

            Screen.CreateChatRoomScreen.name,
            enterTransition = {
                slideInVertically(initialOffsetY = { screenHeight }) + fadeIn(initialAlpha = 0.3f)
            },
            exitTransition = null,
            popEnterTransition = null,
            popExitTransition = {
                slideOutVertically(targetOffsetY = { screenHeight }) + fadeOut(targetAlpha = 0.3f)
            }
        ) { navBackStackEntry ->
            val viewModel: CreateChatRoomViewModel = hiltViewModel(navBackStackEntry)
            val roleState by viewModel.rolesState
            CreateChatRoomScreen(navController, roleState)

        }

    }

}