package com.ubuntuyouiwe.nexus.presentation.login.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.component.text_style.PrimaryClickableText
import com.ubuntuyouiwe.nexus.presentation.ui.theme.DeepBlueSea

@Composable
fun GetLoginSuggestionText(
    onClick: () -> Unit
) {
    val loginText = stringResource(id = R.string.login)
    val combinedLoginSuggestionText = stringResource(id = R.string.login_suggestion, loginText)

    val getLoginSuggestionText = buildAnnotatedString {
        append(combinedLoginSuggestionText)

        val loginStart = combinedLoginSuggestionText.indexOf(loginText)
        val loginEnd = loginStart + loginText.length

        addStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.ExtraBold), loginStart, loginEnd)
        addStringAnnotation(tag = "Destination", annotation = "Login", start = loginStart, end = loginEnd)
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