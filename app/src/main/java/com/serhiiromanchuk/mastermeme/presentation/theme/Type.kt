package com.serhiiromanchuk.mastermeme.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.serhiiromanchuk.mastermeme.R

val Manrope = FontFamily(
    Font(
        resId = R.font.manrope_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.manrope_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.manrope_semibold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.manrope_bold,
        weight = FontWeight.Bold
    ),
)

val Impact = FontFamily(
    Font(
        resId = R.font.impact,
        weight = FontWeight.Normal
    )
)

val ArchivoBlack = FontFamily(
    Font(
        resId = R.font.archivo_black,
        weight = FontWeight.Normal
    )
)

val BebasNeue = FontFamily(
    Font(
        resId = R.font.bebas_neue,
        weight = FontWeight.Normal
    )
)

val Lobster = FontFamily(
    Font(
        resId = R.font.lobster,
        weight = FontWeight.Normal
    )
)

val Oswald = FontFamily(
    Font(
        resId = R.font.oswald,
        weight = FontWeight.Normal
    )
)

val Rubik = FontFamily(
    Font(
        resId = R.font.rubik,
        weight = FontWeight.Normal
    )
)
// Set of Material typography styles to start with
val Typography = Typography(
    labelMedium = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        color = Color.White
    ),
    bodySmall = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 24.sp,
        color = MasterMemeLightGray
    ),
    bodyMedium = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = MasterMemeLightGray
    ),
    titleMedium = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 20.sp,
        color = MasterMemeLightGray
    ),
)