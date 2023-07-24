package com.ubuntuyouiwe.nexus.presentation.main_activity

import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.chuckerteam.chucker.api.Chucker
import com.squareup.seismic.ShakeDetector
import com.ubuntuyouiwe.nexus.BuildConfig
import com.ubuntuyouiwe.nexus.presentation.ui.theme.NexusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), ShakeDetector.Listener {
    private companion object {
        const val BUILD_TYPE_RELEASE = "release"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NexusTheme {
                installSplashScreen().apply {
                    
                }
                //NavHostScreen()
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NexusTheme {

    }
}