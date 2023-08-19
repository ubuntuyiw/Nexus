package com.ubuntuyouiwe.nexus.presentation.component.icon_button_style

import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ubuntuyouiwe.nexus.presentation.ui.theme.DeepBlueSea
import com.ubuntuyouiwe.nexus.presentation.ui.theme.White

@Composable
fun PrimaryVariantIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = DeepBlueSea,
            contentColor = White,
        ),
        content = content
    )
}