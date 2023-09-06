package com.ubuntuyouiwe.nexus.presentation.login.widgets

import android.util.Log
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
import com.ubuntuyouiwe.nexus.presentation.ui.theme.DeepBlueSea

@Composable
fun GetAnnotatedTermsAndPrivacyTextForLoggedInUser() {
    val getAnnotatedTermsAndPrivacyTextForLoggedInUser = buildAnnotatedString {
        append("By logging in, I accept the")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.ExtraBold,
            )
        ) {
            pushStringAnnotation(
                tag = "URL",
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
                tag = "URL",
                annotation = "PrivacyPolicy"
            )
            append(" Privacy Policy ")
            pop()
        }
        append("of the Nexus app.")
    }

    PrimaryClickableText(
        text = getAnnotatedTermsAndPrivacyTextForLoggedInUser,
        onClick = { offset ->
            getAnnotatedTermsAndPrivacyTextForLoggedInUser.getStringAnnotations(
                tag = "URl",
                start = offset, end = offset
            ).firstOrNull()?.let { annotation ->
                when (annotation.item) {
                    "TermsOfUse" -> {
                        Log.v("asdasd", annotation.item)
                    }

                    "PrivacyPolicy" -> {
                        Log.v("asdasd", annotation.item)
                    }
                }
            }
        },
        modifier = Modifier.padding(start = 48.dp, end = 48.dp)

    )
}