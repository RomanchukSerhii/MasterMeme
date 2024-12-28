package com.serhiiromanchuk.mastermeme.presentation.screens.editor.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serhiiromanchuk.mastermeme.presentation.theme.Manrope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeFontSizeBanner(
    modifier: Modifier = Modifier,
    startPosition: Float,
    onValueChange: (Float) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        FontSizeText(fontSize = 12.sp)

        Slider(
            value = startPosition,
            onValueChange = { onValueChange(it) },
            valueRange = 18f..38f,
            steps = 19,
            modifier = Modifier.weight(1f),
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = remember { MutableInteractionSource() },
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.secondary
                    ),
                    enabled = true,
                    thumbSize = DpSize(width = 18.dp, height = 18.dp)
                )
            },
            track = {

                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.secondary)
                )
            }
        )
        FontSizeText(fontSize = 24.sp)
    }
}

@Composable
private fun FontSizeText(
    modifier: Modifier = Modifier,
    fontSize: TextUnit
) {
    Box(
        modifier = modifier.size(36.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Aa",
            fontFamily = Manrope,
            fontWeight = FontWeight.Medium,
            fontSize = fontSize,
            color = Color.White
        )
    }
}

@Composable
fun ChangeColorBanner(
    modifier: Modifier = Modifier,
    colorList: List<Color>,
    onColorClicked: (color: Color) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        items(colorList) { color ->
            Box(
                modifier = Modifier
                    .background(color, CircleShape)
                    .clickable(
                        interactionSource,
                        LocalIndication.current
                    ) {
                        onColorClicked(color)
                    }
            )
        }
    }
}