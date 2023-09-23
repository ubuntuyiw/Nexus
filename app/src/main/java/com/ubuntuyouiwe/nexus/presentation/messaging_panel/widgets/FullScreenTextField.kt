package com.ubuntuyouiwe.nexus.presentation.messaging_panel.widgets

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color

@Composable
fun FullScreenTextField(
    messageText: String,
    maxCharacter: Int,
    onValueChange: (String) -> Unit,
    visibility: Boolean,
    hide: () -> Unit
) {
    val emailStateFocusRequester = remember { FocusRequester() }
    LaunchedEffect(key1 = visibility) {
        if (visibility) {
            emailStateFocusRequester.requestFocus()
        }
    }

    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        TextField(
            value = messageText,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.onSurface,
                selectionColors = TextSelectionColors(
                    MaterialTheme.colorScheme.onPrimary,
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                ),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = MaterialTheme.colorScheme.onSurface,
            ),
            label = if (messageText.length >= maxCharacter) {
                {
                    Text(
                        text = "Message limit exceeded. Maximum 500 characters allowed.",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            } else null,
            enabled = visibility,
            leadingIcon = {
                IconButton(onClick = hide, modifier = Modifier) {
                    Icon(
                        imageVector = Icons.Default.FullscreenExit,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = Icons.Default.FullscreenExit.name
                    )
                }
            },
            suffix = {
                Text(
                    text = messageText.length.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    color = if (messageText.length >= maxCharacter) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.onPrimary
                )
            },
            isError = messageText.length >= maxCharacter,
            modifier = Modifier
                .focusRequester(emailStateFocusRequester)
                .fillMaxHeight()
                .weight(1f)
        )


    }
    BackHandler {
        hide()
    }
}