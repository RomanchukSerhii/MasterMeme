package com.serhiiromanchuk.mastermeme.presentation.screens.editor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.core.components.OutlinedButton
import com.serhiiromanchuk.mastermeme.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.mastermeme.presentation.core.state.MemeTextState
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent
import com.serhiiromanchuk.mastermeme.presentation.theme.Manrope

@Composable
fun EditorBottomBar(
    modifier: Modifier = Modifier,
    memeTextState: MemeTextState,
    editMode: Boolean = false,
    onEvent: (EditorUiEvent) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .height(72.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        if (editMode) {
            EditModeBottomBar(
                fontSize = memeTextState.currentFontSize,
                onValueChange = { fontSize ->
                    onEvent(EditorUiEvent.FontSizeChanged(fontSize = fontSize))
                },
                onResetClicked = { onEvent(EditorUiEvent.ResetEditingClicked(memeTextState.id)) },
                onApplyClicked = { onEvent(EditorUiEvent.ApplyEditingClicked(memeTextState.id)) }
            )
        } else {
            NormalModeBottomBar(
                onAddTextClicked = { onEvent(EditorUiEvent.ShowEditTextDialog(true)) },
                onSaveMemeClicked = { onEvent(EditorUiEvent.SaveMemeClicked) }
            )
        }
    }
}

@Composable
private fun NormalModeBottomBar(
    onAddTextClicked: () -> Unit,
    onSaveMemeClicked: () -> Unit
) {
    Row {
        // AddTextButton
        OutlinedButton(
            modifier = Modifier.padding(end = 16.dp),
            onClick = onAddTextClicked,
            labelText = stringResource(R.string.add_text)
        )

        // SaveMemeButton
        PrimaryButton(
            modifier = Modifier.padding(start = 16.dp),
            onClick = onSaveMemeClicked,
            labelText = stringResource(R.string.save_meme)
        )
    }
}

@Composable
private fun EditModeBottomBar(
    fontSize: Float,
    onValueChange: (Float) -> Unit,
    onResetClicked: () -> Unit,
    onApplyClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        ResetIcon(onClick = onResetClicked)
        Spacer(modifier = Modifier.width(12.dp))
        TextSizeSlider(
            modifier = Modifier.weight(1f),
            startPosition = fontSize,
            onValueChange = onValueChange
        )
        Spacer(modifier = Modifier.width(12.dp))
        ApplyIcon(onClick = onApplyClicked)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextSizeSlider(
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