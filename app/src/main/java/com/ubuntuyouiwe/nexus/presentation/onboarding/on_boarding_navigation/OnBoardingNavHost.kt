package com.ubuntuyouiwe.nexus.presentation.onboarding.on_boarding_navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ubuntuyouiwe.nexus.presentation.onboarding.system_message.SystemMessageScreen
import com.ubuntuyouiwe.nexus.presentation.onboarding.system_message.SystemMessageViewModel
import com.ubuntuyouiwe.nexus.presentation.onboarding.user_name.UserNameViewModel
import com.ubuntuyouiwe.nexus.presentation.onboarding.user_name.UserNameScreen
import com.ubuntuyouiwe.nexus.presentation.onboarding.user_preferences.PurposeSelectionScreen
import com.ubuntuyouiwe.nexus.presentation.onboarding.user_preferences.PurposeSelectionViewModel

@Composable
fun OnBoardingNavHost() {
    /*val navController = rememberNavController()
    NavHost(navController = navController, startDestination = OnBoardingScreen.UserName.name) {
        composable(OnBoardingScreen.UserName.name) { navBackStackEntry ->
            val viewModel: UserNameViewModel = hiltViewModel(navBackStackEntry)
            val userName by viewModel.userName
            val updateDisplayNameState by viewModel.updateDisplayNameState
            val onEvent = viewModel::onEvent

            UserNameScreen(
                navController,
                userName,
                updateDisplayNameState,
                onEvent
            )
        }

        composable(OnBoardingScreen.PurposeSelection.name) { navBackStackEntry ->
            val viewModel: PurposeSelectionViewModel = hiltViewModel(navBackStackEntry)
            val purposeSelectionState by viewModel.getPurposeSelection
            val updatePurposeSelection by viewModel.updatePurposeSelection
            val onEvent = viewModel::onEvent
            PurposeSelectionScreen(
                navController,
                purposeSelectionState,
                updatePurposeSelection,
                onEvent

            )
        }
        composable(OnBoardingScreen.SystemMessage.name) { navBackStackEntry ->
            val viewModel: SystemMessageViewModel = hiltViewModel(navBackStackEntry)
            val systemMessage by viewModel.systemMessage
            val updateSystemMessageState by viewModel.updateSystemMessageState
            val onEvent = viewModel::onEvent
            SystemMessageScreen(systemMessage, updateSystemMessageState, onEvent)
        }

    }*/
}