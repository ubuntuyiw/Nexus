package com.ubuntuyouiwe.nexus.presentation.main_activity

import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.chuckerteam.chucker.api.Chucker
import com.squareup.seismic.ShakeDetector
import com.ubuntuyouiwe.nexus.BuildConfig
import com.ubuntuyouiwe.nexus.presentation.main_activity.widgets.UserOperationErrorUi
import com.ubuntuyouiwe.nexus.presentation.navigation.NavHostScreen
import com.ubuntuyouiwe.nexus.presentation.navigation.Screen
import com.ubuntuyouiwe.nexus.presentation.ui.theme.NexusTheme
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), ShakeDetector.Listener {
    private companion object {
        const val BUILD_TYPE_RELEASE = "release"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainActivityViewModel = hiltViewModel()
            val mainAuthUiEvent by viewModel.userOperationState
            val authListenerRetryButtonState by viewModel.authListenerRetryButton
            var startDestination by remember {
                mutableStateOf(Screen.SPLASH)
            }
            NexusTheme {
                installSplashScreen()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = White
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


        if (!BUILD_TYPE_RELEASE.equals(BuildConfig.BUILD_TYPE, true)) {
            val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
            val shakeDetector = ShakeDetector(this)
            shakeDetector.start(sensorManager, SensorManager.SENSOR_DELAY_GAME)
        }


    }

    override fun hearShake() {
        startActivity(Chucker.getLaunchIntent(this))
    }
}

