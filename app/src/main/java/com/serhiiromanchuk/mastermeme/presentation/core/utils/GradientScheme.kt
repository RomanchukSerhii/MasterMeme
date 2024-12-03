package com.serhiiromanchuk.mastermeme.presentation.core.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.serhiiromanchuk.mastermeme.presentation.theme.MasterMemeLightPurple
import com.serhiiromanchuk.mastermeme.presentation.theme.MasterMemePurple

object GradientScheme {
    val PrimaryGradient = Brush.horizontalGradient(
        colors = listOf(
            MasterMemeLightPurple,
            MasterMemePurple
        )
    )
    val ShadowGradient = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color(0xFF141218)
        )
    )
}