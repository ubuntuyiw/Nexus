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
fun SignUpPrompt(
    onClick: () -> Unit
) {
    val signUpText = stringResource(id = R.string.signup)
    val combinedSignUpPromptText = stringResource(id = R.string.sign_up_prompt, signUpText)

    val signUpPrompt = buildAnnotatedString {
        append(combinedSignUpPromptText)

        val signUpTextStart = combinedSignUpPromptText.indexOf(signUpText)
        val signUpTextEnd = signUpTextStart + signUpText.length

        addStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.ExtraBold), signUpTextStart, signUpTextEnd)
        addStringAnnotation(tag = "Destination", annotation = "SignUp", start = signUpTextStart, end = signUpTextEnd)
    }
    PrimaryClickableText(
        text = signUpPrompt,
        onClick = { offset ->
            signUpPrompt.getStringAnnotations(
                tag = "Destination",
                start = offset, end = offset
            ).firstOrNull()?.let { _ ->
                onClick()
            }
        }

    )
}