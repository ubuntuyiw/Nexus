package com.ubuntuyouiwe.nexus.presentation.login.auth_choice.widgets

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.ubuntuyouiwe.nexus.R

@Composable
fun AppNameAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val nexus = stringResource(id = R.string.app_name)
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    val gradientColors = listOf(MaterialTheme.colorScheme.onPrimary, MaterialTheme.colorScheme.primary)
    val gradientColorsAnimation = listOf(
        gradientColors[0].copy(alpha = alpha),
        gradientColors[1].copy(alpha = 1f - alpha)
    )

    val textStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.rosmatika_regular)),
        fontSize = 32.sp,
        letterSpacing = 8.sp,
        brush = Brush.horizontalGradient(
            colors = gradientColorsAnimation
        )
    )
    Text(
        text = nexus,
        style = textStyle
    )
}