package com.serhiiromanchuk.mastermeme.presentation.core.utils

import androidx.compose.ui.graphics.Brush
import com.serhiiromanchuk.mastermeme.presentation.theme.MasterMemeLightPurple
import com.serhiiromanchuk.mastermeme.presentation.theme.MasterMemePurple

object GradientScheme {
    val PrimaryGradient = Brush.horizontalGradient(
        colors = listOf(
            MasterMemeLightPurple,
            MasterMemePurple
        )
    )
}