package com.ubuntuyouiwe.nexus.presentation.login.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.R
import com.ubuntuyouiwe.nexus.presentation.component.text_style.PrimaryClickableText

@Composable
fun GetAnnotatedTermsAndPrivacyText(
    termsOfUseOnClick: () -> Unit,
    privacyPolicy: () -> Unit
) {
    val termsText = stringResource(id = R.string.terms_of_use)
    val privacyText = stringResource(id = R.string.privacy_policy)
    val combinedTextResource = stringResource(id = R.string.signup_acceptance, termsText, privacyText)

    val getAnnotatedTermsAndPrivacyText = buildAnnotatedString {
        append(combinedTextResource)

        val termsStart = combinedTextResource.indexOf(termsText)
        val termsEnd = termsStart + termsText.length

        val privacyStart = combinedTextResource.indexOf(privacyText)
        val privacyEnd = privacyStart + privacyText.length

        addStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.ExtraBold), termsStart, termsEnd)
        addStringAnnotation(tag = "URL", annotation = "TermsOfUse", start = termsStart, end = termsEnd)

        addStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.ExtraBold), privacyStart, privacyEnd)
        addStringAnnotation(tag = "URL", annotation = "PrivacyPolicy", start = privacyStart, end = privacyEnd)
    }

    PrimaryClickableText(
        text = getAnnotatedTermsAndPrivacyText,
        onClick = { offset ->
            getAnnotatedTermsAndPrivacyText.getStringAnnotations(
                tag = "URL",
                start = offset, end = offset
            ).firstOrNull()?.let { annotation ->
                when (annotation.item) {
                    "TermsOfUse" -> {
                        termsOfUseOnClick()
                    }

                    "PrivacyPolicy" -> {
                        privacyPolicy()
                    }
                }
            }
        },
        modifier = Modifier.padding(start = 24.dp, end = 24.dp)

    )
}