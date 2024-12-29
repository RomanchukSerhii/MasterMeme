@file:OptIn(ExperimentalComposeUiApi::class)

package com.serhiiromanchuk.mastermeme.presentation.screens.editor.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.core.components.OutlinedText
import com.serhiiromanchuk.mastermeme.presentation.core.utils.FontColorProvider
import com.serhiiromanchuk.mastermeme.presentation.core.utils.FontProvider
import com.serhiiromanchuk.mastermeme.presentation.core.utils.MemeFont
import com.serhiiromanchuk.mastermeme.presentation.theme.Manrope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontSizeBanner(
    modifier: Modifier = Modifier,
    startPosition: Float,
    onValueChange: (Float) -> Unit
) {
    Row(
        modifier = modifier.padding(vertical = 12.dp),
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
fun ColorPickerBanner(
    modifier: Modifier = Modifier,
    colorList: List<Color> = FontColorProvider.colorList,
    currentColor: Color,
    onColorClicked: (color: Color) -> Unit
) {
    LazyRow(
        modifier = modifier.padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(colorList) { color ->
            FontColorBox(
                color = color,
                isSelected = currentColor == color,
                onColorClicked = onColorClicked
            )
        }
    }
}

@Composable
private fun FontColorBox(
    modifier: Modifier = Modifier,
    color: Color,
    isSelected: Boolean,
    onColorClicked: (color: Color) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                interactionSource,
                LocalIndication.current
            ) {
                onColorClicked(color)
            }
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.surfaceContainerHigh
                } else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(6.dp)

    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(color, CircleShape)

        )
    }
}

@Composable
fun FontFamilyBanner(
    modifier: Modifier = Modifier,
    memeFontList: List<MemeFont> = FontProvider.fontList,
    currentFont: FontFamily,
    onFontClicked: (FontFamily) -> Unit
) {
    LazyRow(
        modifier = modifier.padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        items(memeFontList) { memeFont ->
            FontFamilyBox(
                fontFamily = memeFont.fontFamily,
                fontName = memeFont.fontName,
                isSelected = currentFont == memeFont.fontFamily,
                onClicked = onFontClicked
            )
        }
    }
}

@Composable
private fun FontFamilyBox(
    fontFamily: FontFamily,
    fontName: String,
    isSelected: Boolean,
    onClicked: (FontFamily) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                interactionSource,
                LocalIndication.current
            ) {
                onClicked(fontFamily)
            }
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.surfaceContainerHigh
                } else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp)
            .padding(top = 4.dp, bottom = 8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedText(
                text = stringResource(R.string.font_example),
                fontFamily = fontFamily,
                fontSize = 28.sp
            )
            Text(
                text = fontName,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}