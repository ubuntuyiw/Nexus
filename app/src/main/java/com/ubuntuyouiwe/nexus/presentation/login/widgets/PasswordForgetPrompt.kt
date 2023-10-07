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
fun PasswordForgetPrompt(
    onClick: () -> Unit
) {
    val forgotPasswordText = stringResource(id = R.string.forgot_password)
    val combinedForgotPasswordPromptText = stringResource(id = R.string.password_forget_prompt, forgotPasswordText)

    val passwordForgetPrompt = buildAnnotatedString {
        append(combinedForgotPasswordPromptText)

        val forgotPasswordStart = combinedForgotPasswordPromptText.indexOf(forgotPasswordText)
        val forgotPasswordEnd = forgotPasswordStart + forgotPasswordText.length

        addStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.ExtraBold), forgotPasswordStart, forgotPasswordEnd)
        addStringAnnotation(tag = "Destination", annotation = "Forgot", start = forgotPasswordStart, end = forgotPasswordEnd)
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