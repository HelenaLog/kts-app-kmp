package com.github.helenalog.kts_kmp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import ktsappkmp.composeapp.generated.resources.Res
import ktsappkmp.composeapp.generated.resources.roboto_medium
import ktsappkmp.composeapp.generated.resources.roboto_regular
import ktsappkmp.composeapp.generated.resources.roboto_semibold
import org.jetbrains.compose.resources.Font

@Composable
fun rememberAppTypography(): Typography {
    val roboto = FontFamily(
        Font(Res.font.roboto_regular, FontWeight.Normal),
        Font(Res.font.roboto_medium, FontWeight.Medium),
        Font(Res.font.roboto_semibold, FontWeight.SemiBold),
    )
    return Typography(
        headlineLarge = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Bold,
            fontSize = 42.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
        ),
        headlineSmall = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 28.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        labelLarge = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
    )
}