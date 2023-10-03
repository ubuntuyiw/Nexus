package com.ubuntuyouiwe.nexus.presentation.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = DeepBlueSeaDark,
    onPrimary = White,
    inversePrimary = Slate,
    secondary = aa,
    onSecondary = White,
    background = DeepTwilight,
    onBackground = White,
    surface = DeepBlueSeaDark,
    surfaceVariant = White,
    onSurfaceVariant = Black,
    onSurface = White,
    error = Color.Red,
    onError = White,
    errorContainer = DeepTwilight,
    onErrorContainer = White,
    scrim = scrim
)

private val LightColorScheme = lightColorScheme(
    primary = DeepBlueSea,
    onPrimary = Color.White,
    secondary = DeepBlueSea,
    onSecondary = Color.White,
    background = Whisper,
    onBackground = Color.Black,
    surface = White,
    onSurface = Color.Black,
    error = Color.Red,
    onError = Color.White,
)


@Composable
fun NexusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.navigationBarColor =  colorScheme.background.toArgb()
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}