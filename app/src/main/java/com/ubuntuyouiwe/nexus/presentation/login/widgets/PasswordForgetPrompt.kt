package com.ubuntuyouiwe.nexus.presentation.login.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.ubuntuyouiwe.nexus.presentation.component.text_style.PrimaryClickableText
import com.ubuntuyouiwe.nexus.presentation.ui.theme.DeepBlueSea

@Composable
fun PasswordForgetPrompt(
    onClick: () -> Unit
) {
    val passwordForgetPrompt  = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = DeepBlueSea,
                fontWeight = FontWeight.ExtraBold,
            )
        ) {
            pushStringAnnotation(
                tag = "Destination",
                annotation = "Forgot"
            )
            append("Forgot your password?")
            pop()
        }
    }

    PrimaryClickableText(
        text = passwordForgetPrompt,
        onClick = { offset ->
            passwordForgetPrompt.getStringAnnotations(
                tag = "Destination",
                start = offset, end = offset
            ).firstOrNull()?.let { annotation ->
                onClick()
            }
        }

    )
}