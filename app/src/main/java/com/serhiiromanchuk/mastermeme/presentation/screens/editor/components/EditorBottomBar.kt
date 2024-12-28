package com.serhiiromanchuk.mastermeme.presentation.screens.editor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.core.components.OutlinedButton
import com.serhiiromanchuk.mastermeme.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.mastermeme.presentation.core.state.MemeTextState
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent

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
        ChangeFontSizeBanner(
            modifier = Modifier.weight(1f),
            startPosition = fontSize,
            onValueChange = onValueChange
        )
        Spacer(modifier = Modifier.width(12.dp))
        ApplyIcon(onClick = onApplyClicked)
    }
}