package com.ubuntuyouiwe.nexus.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.ubuntuyouiwe.nexus.R


@OptIn(ExperimentalTextApi::class)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontSize = 15.sp,
        letterSpacing = 0.5.sp,

        ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontSize = 14.sp,
        letterSpacing = 0.sp,

        ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontSize = 13.sp,
        letterSpacing = 0.sp,

        ),
    displayLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.rosmatika_regular)),
        fontSize = 36.sp,
        letterSpacing = 16.sp

    ),


    displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontSize = 20.sp,

    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontSize = 18.sp,
        letterSpacing = 2.sp,

        ),

    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontSize = 16.sp

    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontSize = 14.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontSize = 13.sp,
        textAlign = TextAlign.Center,
        color = Gray,
        lineHeight = 18.sp

    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontSize = 12.sp,
        textAlign = TextAlign.Center,
        color = Gray,
        lineHeight = 18.sp

    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.rosmatika_regular)),
        fontSize = 11.sp,
        textAlign = TextAlign.Center,
        letterSpacing = 2.sp,
        lineHeight = 24.sp
    )

)