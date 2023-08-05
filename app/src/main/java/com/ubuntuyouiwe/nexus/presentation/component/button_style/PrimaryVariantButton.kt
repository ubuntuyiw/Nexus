package com.ubuntuyouiwe.nexus.presentation.component.button_style

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Black
import com.ubuntuyouiwe.nexus.presentation.ui.theme.SoftGray

@Composable
fun PrimaryVariantButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        border = BorderStroke(1.dp, SoftGray),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Black
        ),
        content = content
    )
}