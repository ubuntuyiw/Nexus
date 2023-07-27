package com.ubuntuyouiwe.nexus.presentation.component.text_style

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.ubuntuyouiwe.nexus.presentation.ui.theme.DeepBlueSea
import com.ubuntuyouiwe.nexus.presentation.ui.theme.Gray

@Composable
fun PrimaryClickableText(
    text: String,
    clickText: String,
    clickable: () -> Unit,

) {
    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Gray,
            )
        ) {
            append(text)
        }


        pushStringAnnotation(
            tag = "SignUp",
            annotation = "SignUp"
        )
        withStyle(
            style = SpanStyle(
                color = DeepBlueSea,
            )
        ) {
            append(clickText)
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            val annotations = annotatedText.getStringAnnotations(
                tag = "SignUp",
                start = offset,
                end = offset
            )
            if (annotations.isNotEmpty()) {
                clickable()
            }
        }
    )
}