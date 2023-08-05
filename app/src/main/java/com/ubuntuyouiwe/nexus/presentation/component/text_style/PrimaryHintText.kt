package com.ubuntuyouiwe.nexus.presentation.component.text_style

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ubuntuyouiwe.nexus.presentation.ui.theme.DarkGray

@Composable
fun PrimaryHintText(
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = DarkGray
    )
}