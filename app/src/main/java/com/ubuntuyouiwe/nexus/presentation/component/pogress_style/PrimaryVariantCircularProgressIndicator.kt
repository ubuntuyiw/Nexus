package com.ubuntuyouiwe.nexus.presentation.component.pogress_style

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryVariantCircularProgressIndicator() {
    CircularProgressIndicator(
        modifier = Modifier
            .padding(end = 16.dp)
            .size(28.dp),
        strokeCap = StrokeCap.Square,
        strokeWidth = 2.dp,
    )
}