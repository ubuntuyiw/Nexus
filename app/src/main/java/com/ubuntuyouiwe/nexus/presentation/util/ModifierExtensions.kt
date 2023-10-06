package com.ubuntuyouiwe.nexus.presentation.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalFoundationApi::class)
@Stable
fun Modifier.customCombinedClickable(
    whetherOrNot: Boolean,
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit,
    onLongClick: () -> Unit
): Modifier =
    if (whetherOrNot) {
        this.then(
            Modifier.combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
                onLongClick = onLongClick

            )
        )
    } else this.then(
        Modifier.combinedClickable(
            onClick = onClick,
            onLongClick = onLongClick
        )
    )