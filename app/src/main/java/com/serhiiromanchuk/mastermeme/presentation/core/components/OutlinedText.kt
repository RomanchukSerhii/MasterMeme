package com.serhiiromanchuk.mastermeme.presentation.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.serhiiromanchuk.mastermeme.presentation.theme.Impact

@ExperimentalComposeUiApi
@Composable
fun OutlinedText(
    text: String,
    fontFamily: FontFamily = Impact,
    fontSize: TextUnit,
    color: Color = Color.White,
    modifier: Modifier = Modifier,
) {
    val textStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = fontSize,
        color = color
    )
    val letterSpacing = TextUnit(0F, TextUnitType.Sp)

    Box(modifier = modifier,) {
        Text(
            text = text,
            letterSpacing = letterSpacing,
            style = textStyle,
        )

        Text(
            text = text,
            modifier = Modifier
                .semantics { invisibleToUser() },
            color = Color.Black,
            letterSpacing = letterSpacing,
            textDecoration = null,
            style = textStyle.copy(
                shadow = null,
                drawStyle = Stroke(width = 3f),
            ),
        )
    }
}