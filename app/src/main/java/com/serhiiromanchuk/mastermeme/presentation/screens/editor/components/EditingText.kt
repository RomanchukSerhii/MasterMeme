package com.serhiiromanchuk.mastermeme.presentation.screens.editor.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.serhiiromanchuk.mastermeme.presentation.core.components.OutlinedText
import com.serhiiromanchuk.mastermeme.presentation.theme.Impact

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditingText(
    modifier: Modifier = Modifier,
    text: String
) {
    OutlinedText(
        modifier = modifier,
        text = text,
        style = TextStyle(
            fontFamily = Impact,
            fontSize = 28.sp,
            color = Color.White
        ),
        outlineColor = Color.Black,
        letterSpacing = TextUnit(0F, TextUnitType.Sp)
    )
}