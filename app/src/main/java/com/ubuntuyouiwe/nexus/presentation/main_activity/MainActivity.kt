package com.ubuntuyouiwe.nexus.presentation.main_activity

import android.content.pm.ActivityInfo
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions
import com.squareup.seismic.ShakeDetector
import com.squareup.seismic.ShakeDetector.SENSITIVITY_LIGHT
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.domain.model.User
import com.ubuntuyouiwe.nexus.presentation.main_activity.widgets.NotificationPermission
import com.ubuntuyouiwe.nexus.presentation.main_activity.widgets.UserOperationErrorUi
import com.ubuntuyouiwe.nexus.presentation.navigation.NavHostScreen
import com.ubuntuyouiwe.nexus.presentation.ui.theme.NexusTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity(), ShakeDetector.Listener {
    private val viewModel: MainActivityViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU, Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this)

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val shakeDetector = ShakeDetector(this)
        shakeDetector.setSensitivity(SENSITIVITY_LIGHT)
        shakeDetector.start(sensorManager, SensorManager.SENSOR_DELAY_GAME)

        setContent {
            val hostState = remember {
                SnackbarHostState()
            }
            NotificationPermission(
                hostState,
            )
            val systemLanguage = Locale.getDefault().language.uppercase(Locale.ROOT)

            val getTokenState by viewModel.getTokenState

            val mainAuthUiEvent by viewModel.userOperationState
            val user by viewModel.userState
            val settingsState by viewModel.settingsState
            var isDarkTheme by remember {
                mutableStateOf<Boolean?>(null)
            }
            when (settingsState.successData.theme) {
                0 -> isDarkTheme = true
                1 -> isDarkTheme = false
                2 -> isDarkTheme = null
            }


            val authListenerRetryButtonState by viewModel.authListenerRetryButton
            LaunchedEffect(key1 = user.successData) {
                user.successData?.let { user ->
                    mainAuthUiEvent.successData?.let { auth ->
                        if (user.shouldLogout && !user.isFromCache && !user.hasPendingWrites) {
                            viewModel.logOut(getTokenState.successData)
                        }
                    }
                }
            }

            val startDestination by viewModel.startDestination
            LaunchedEffect(key1 = mainAuthUiEvent) {
                mainAuthUiEvent.successData?.let {
                    viewModel.getUser(it.uid)
                    viewModel.getUserMessagingData(it.uid)

                    if (getTokenState.isSuccess) {
                        viewModel.saveToken(getTokenState.successData)
                    }
                    viewModel.saveSystemLanguage(systemLanguage)

                } ?: run {
                    viewModel.getUserJob?.cancel()
                    viewModel.getUserMessagingJob?.cancel()
                }

            }

            NexusTheme(
                isDarkTheme ?: isSystemInDarkTheme()
            ) {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(hostState) {
                                Snackbar(snackbarData = it)
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize()
                        ) {
                            NavHostScreen(startDestination)
                            if (mainAuthUiEvent.isError) {
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

            }
        }


        val isTablet = resources.getBoolean(R.bool.isTablet)
        requestedOrientation = if (isTablet) {
            ActivityInfo.SCREEN_ORIENTATION_SENSOR
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

    }


    private var isShowAd: Boolean = true

    override fun hearShake() {
        val mainAuthUiEvent by viewModel.userOperationState
        mainAuthUiEvent.successData?.let { user ->
            loadAd(user)
        }
    }


    override fun onStart() {
        super.onStart()
        Log.v("asdasd", "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.v("asdasd", "onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.v("asdasd", "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.v("asdasd", "onResume")
        isShowAd = true
    }

    private fun loadAd(user: User) {
        if (!isShowAd) return
        else isShowAd = false
        val loadingAd = this.resources.getString(R.string.loading_ad)
        val adsUnavailable = this.resources.getString(R.string.ads_unavailable)
        Toast.makeText(this@MainActivity, loadingAd, Toast.LENGTH_SHORT).show()
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            this,
            "ca-app-pub-8437475970369583/4956584331",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Toast.makeText(
                        this@MainActivity,
                        adsUnavailable,
                        Toast.LENGTH_SHORT
                    ).show()
                    isShowAd = true
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    val options = ServerSideVerificationOptions.Builder()
                        .setCustomData(user.uid)
                        .setUserId(user.uid)
                        .build()
                    ad.setServerSideVerificationOptions(options)
                    ad.setOnPaidEventListener {
                        val value: Double = it.valueMicros.toDouble() / 1000000
                        Log.v("asdasd", it.currencyCode)
                        Log.v("asdasd", it.precisionType.toString())
                        Log.v("asdasd", String.format("%.6f", value))
                    }
                    ad.show(this@MainActivity) { _ ->
                        Toast.makeText(
                            this@MainActivity,
                            "Reward loaded.",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.v("asdasd", user.uid)
                        isShowAd = true


                    }

                }
            })
    }


}

