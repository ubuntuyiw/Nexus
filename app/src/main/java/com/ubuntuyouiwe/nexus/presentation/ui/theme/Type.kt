package com.ubuntuyouiwe.nexus.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.ubuntuyouiwe.nexus.R


@OptIn(ExperimentalTextApi::class)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,

    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,

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

    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.rosmatika_regular)),
        fontSize = 28.sp,
        letterSpacing = 8.sp,
        brush = Brush.linearGradient(
            colors = gradientColors)

    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontSize = 18.sp,
        letterSpacing = 2.sp,

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