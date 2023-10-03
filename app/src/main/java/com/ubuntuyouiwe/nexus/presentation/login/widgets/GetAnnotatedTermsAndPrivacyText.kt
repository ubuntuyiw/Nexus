package com.ubuntuyouiwe.nexus.presentation.login.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ubuntuyouiwe.nexus.presentation.component.text_style.PrimaryClickableText

@Composable
fun GetAnnotatedTermsAndPrivacyText(
    termsOfUseOnClick: () -> Unit,
    privacyPolicy: () -> Unit
) {
    val getAnnotatedTermsAndPrivacyText = buildAnnotatedString {
        append("By signing up, I accept the")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.ExtraBold,
            )
        ) {
            pushStringAnnotation(
                tag = "URl",
                annotation = "TermsOfUse"
            )
            append(" Terms of Use ")
            pop()
        }
        append("and")

        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.ExtraBold,
            )
        ) {
            pushStringAnnotation(
                tag = "URl",
                annotation = "PrivacyPolicy"
            )
            append(" Privacy Policy ")
            pop()
        }
        append("of the Nexus app.")
    }

    PrimaryClickableText(
        text = getAnnotatedTermsAndPrivacyText,
        onClick = { offset ->
            getAnnotatedTermsAndPrivacyText.getStringAnnotations(
                tag = "URl",
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