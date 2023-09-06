package com.ubuntuyouiwe.nexus.presentation.login.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.ubuntuyouiwe.nexus.presentation.component.text_style.PrimaryClickableText
import com.ubuntuyouiwe.nexus.presentation.ui.theme.DeepBlueSea

@Composable
fun GetLoginSuggestionText(
    onClick: () -> Unit
) {
    val getLoginSuggestionText = buildAnnotatedString {
        append("Do you already have an account?")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.ExtraBold,
            )
        ) {
            pushStringAnnotation(
                tag = "Destination",
                annotation = "Login"
            )
            append(" Login.")
            pop()
        }
    }
    PrimaryClickableText(
        text = getLoginSuggestionText,
        onClick = { offset ->
            getLoginSuggestionText.getStringAnnotations(
                tag = "Destination",
                start = offset, end = offset
            ).firstOrNull()?.let { _ ->
                onClick()
            }
        }

    )
}