package com.ubuntuyouiwe.nexus.presentation.component.text_style

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString

@Composable
fun PrimaryClickableText(
    text: AnnotatedString,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier

) {
    ClickableText(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        onClick = onClick,
        modifier = modifier
    )

}