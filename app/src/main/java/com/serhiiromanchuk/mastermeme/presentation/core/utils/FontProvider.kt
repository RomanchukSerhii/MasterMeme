package com.serhiiromanchuk.mastermeme.presentation.core.utils

import androidx.compose.ui.text.font.FontFamily
import com.serhiiromanchuk.mastermeme.presentation.theme.ArchivoBlack
import com.serhiiromanchuk.mastermeme.presentation.theme.BebasNeue
import com.serhiiromanchuk.mastermeme.presentation.theme.Impact
import com.serhiiromanchuk.mastermeme.presentation.theme.Lobster
import com.serhiiromanchuk.mastermeme.presentation.theme.Oswald
import com.serhiiromanchuk.mastermeme.presentation.theme.Rubik

object FontProvider {
    val fontList = listOf(
        MemeFont(
            fontName = "Impact",
            fontFamily = Impact
        ),
        MemeFont(
            fontName = "Oswald",
            fontFamily = Oswald
        ),
        MemeFont(
            fontName = "Bebas Neue",
            fontFamily = BebasNeue
        ),
        MemeFont(
            fontName = "Rubik",
            fontFamily = Rubik
        ),
        MemeFont(
            fontName = "Lobster",
            fontFamily = Lobster
        ),
        MemeFont(
            fontName = "Archivo Black",
            fontFamily = ArchivoBlack
        ),
    )
}

data class MemeFont(
    val fontName: String,
    val fontFamily: FontFamily
)