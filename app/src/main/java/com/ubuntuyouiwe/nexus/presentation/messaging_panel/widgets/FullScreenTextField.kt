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
import androidx.compose.ui.res.stringResource
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.component.text_field_style.PrimaryTextField

@Composable
fun FullScreenTextField(
    messageText: String,
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
        PrimaryTextField(
            value = messageText,
            onValueChange = onValueChange,
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