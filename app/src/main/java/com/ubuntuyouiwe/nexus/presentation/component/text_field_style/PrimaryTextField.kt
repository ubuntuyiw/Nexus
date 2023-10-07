package com.ubuntuyouiwe.nexus.presentation.component.text_field_style

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PrimaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    visualChar: Char? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = false,
    isError: Boolean = false,
    enabled: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    suffix: @Composable (() -> Unit)? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    leadingIcon: (@Composable () -> Unit)? = null
) {

    val visualTransformation = if (visualChar != null)
        VisualTransformation { text ->
            val transformedText = visualChar.toString().repeat(text.text.length)
            TransformedText(AnnotatedString(transformedText), OffsetMapping.Identity)
        }
    else VisualTransformation.None
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        maxLines = maxLines,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
            errorContainerColor = MaterialTheme.colorScheme.errorContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondary,
            focusedTextColor = MaterialTheme.colorScheme.onSecondary,
            errorTextColor = MaterialTheme.colorScheme.error,
            disabledTextColor = MaterialTheme.colorScheme.onSecondary,
            cursorColor = MaterialTheme.colorScheme.onSecondary,
            focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
            disabledLabelColor = MaterialTheme.colorScheme.onSecondary,
            errorLabelColor = MaterialTheme.colorScheme.error,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSecondary,
            errorPlaceholderColor = MaterialTheme.colorScheme.error,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onSecondary,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSecondary,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSecondary,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSecondary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSecondary,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            selectionColors = TextSelectionColors(
                MaterialTheme.colorScheme.onPrimary,
                MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.4f),
            ),


        ),
        suffix = suffix,
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = isError,
        modifier = modifier
    )
}