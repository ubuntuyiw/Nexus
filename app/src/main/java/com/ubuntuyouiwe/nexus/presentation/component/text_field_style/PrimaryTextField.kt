package com.ubuntuyouiwe.nexus.presentation.component.text_field_style

import androidx.compose.foundation.text.KeyboardOptions
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
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            disabledTextColor = Color.Black
        ),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = isError,
        modifier = modifier
    )
}