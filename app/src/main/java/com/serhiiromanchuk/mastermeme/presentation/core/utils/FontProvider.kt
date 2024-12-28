package com.serhiiromanchuk.mastermeme.presentation.core.utils

import androidx.compose.ui.text.font.FontFamily
import com.serhiiromanchuk.mastermeme.presentation.theme.Impact

object FontProvider {
    val fontList = listOf(
        MemeFont(
            fontName = "impact",
            fontFamily = Impact
        )
    )
}

data class MemeFont(
    val fontName: String,
    val fontFamily: FontFamily
)