package com.ubuntuyouiwe.nexus.presentation.login.auth_choice.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LogoAnimation() {
    val scale = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val logo = stringResource(id = R.string.logo)
    val scaleEffect by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,

            ), label = ""
    )
    SideEffect {
        coroutineScope.launch {
            scale.animateTo(scaleEffect)
            delay(2000)
            scale.stop()
        }
    }
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = logo,
        modifier = Modifier
            .size(150.dp)
            .scale(scale.value)
    )

}