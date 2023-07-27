package com.ubuntuyouiwe.nexus.presentation.component.icon_style

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Black
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White

@Composable
fun PrimaryIcon(
    painter: Painter,
    contentDescription: String
) {
    Icon(
        painter = painter,
        contentDescription = contentDescription,
        tint = Black
    )
}