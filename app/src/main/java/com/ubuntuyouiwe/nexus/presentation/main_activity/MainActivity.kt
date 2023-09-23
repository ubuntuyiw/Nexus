package com.ubuntuyouiwe.nexus.presentation.main_activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.ads.MobileAds
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.main_activity.widgets.UserOperationErrorUi
import com.ubuntuyouiwe.nexus.presentation.navigation.NavHostScreen
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.ui.theme.NexusTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainActivityViewModel = hiltViewModel()

            val mainAuthUiEvent by viewModel.userOperationState
            val user by viewModel.userState

            val authListenerRetryButtonState by viewModel.authListenerRetryButton

            LaunchedEffect(key1 = user.successData) {
                user.successData?.let {user ->
                    mainAuthUiEvent.successData?.let { auth ->
                        if (user.shouldLogout && !user.isFromCache && !user.hasPendingWrites) {
                            viewModel.logOut()
                        }
                    }
                }
            }

            var startDestination by remember {
                mutableStateOf(Screen.SPLASH)
            }
            LaunchedEffect(key1 = mainAuthUiEvent) {
                mainAuthUiEvent.successData?.let {
                    viewModel.getUser(it.uid)
                    viewModel.getUserMessagingData(it.uid)
                }?: run {
                    viewModel.getUserJob?.cancel()
                    viewModel.getUserMessagingJob?.cancel()
                }

            }

            NexusTheme(
                viewModel.isDarkMode.value
            ) {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHostScreen(startDestination)

                    if (mainAuthUiEvent.isSuccess) {
                        mainAuthUiEvent.successData?.let {
                            startDestination = Screen.CHAT_DASHBOARD
                        } ?: run {
                            startDestination = Screen.AUTHENTICATION_CHOICE
                        }

                    } else if (mainAuthUiEvent.isLoading) {
                        startDestination = Screen.SPLASH
                    } else if (mainAuthUiEvent.isError) {
                        UserOperationErrorUi(
                            errorMessage = mainAuthUiEvent.errorMessage,
                            authListenerRetryButtonState
                        ) {
                            viewModel.onEvent(MainEvent.Retry)
                        }
                    }

                }


            }
        }
         MobileAds.initialize(this)
         val isTablet = resources.getBoolean(R.bool.isTablet)
         requestedOrientation = if (isTablet) {
             ActivityInfo.SCREEN_ORIENTATION_SENSOR
         } else {
             ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
         }



    }
}

