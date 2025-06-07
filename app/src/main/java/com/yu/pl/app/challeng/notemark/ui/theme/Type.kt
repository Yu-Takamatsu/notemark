package com.yu.pl.app.challeng.notemark.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.yu.pl.app.challeng.notemark.R

val SpaceGroteskFontFamily = FontFamily(
    Font(
        resId = R.font.space_grotesk_bold,
        weight = FontWeight.W700,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.space_grotesk_semibold,
        weight = FontWeight.W500,
        style = FontStyle.Normal
    )
)

val InterFontFamily = FontFamily(
    Font(
        resId = R.font.inter_18pt_regular,
        weight = FontWeight.W400,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.inter_18pt_semibold,
        weight = FontWeight.W500,
        style = FontStyle.Normal
    )
)


// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = SpaceGroteskFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 32.sp,
        lineHeight = 36.sp,
        letterSpacing = TextUnit(0.01f, TextUnitType.Sp),
    ),
    titleSmall = TextStyle(
        fontFamily = SpaceGroteskFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 17.sp,
        lineHeight = 24.sp,
    //    letterSpacing = TextUnit(0f, TextUnitType.Sp),
    ),


    bodyLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = TextUnit(0.01f, TextUnitType.Sp),
    ),
    bodyMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = TextUnit(0f, TextUnitType.Sp),
    ),
    bodySmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = TextUnit(0f, TextUnitType.Sp),
    ),
)

val Typography.titleXLarge: TextStyle
    get() = TextStyle(
        fontFamily = SpaceGroteskFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 36.sp,
        lineHeight = 40.sp,
        letterSpacing = TextUnit(0.01f, TextUnitType.Sp),
    )


