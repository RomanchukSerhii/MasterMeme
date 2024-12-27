package com.serhiiromanchuk.mastermeme.presentation.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun BoxScope.BoxFade(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .drawBehind {
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf( Color.Transparent, Color(0xFF141218)),
                        startY = 0f,
                        endY = size.height
                    )
                )
            }
    )
}