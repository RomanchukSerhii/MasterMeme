package com.serhiiromanchuk.mastermeme.presentation.screens.editor.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.core.components.OutlinedButton
import com.serhiiromanchuk.mastermeme.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.state.MemeTextState
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.state.BottomBarState

@Composable
fun EditorBottomBar(
    modifier: Modifier = Modifier,
    memeTextState: MemeTextState,
    bottomBarState: BottomBarState,
    editMode: Boolean = false,
    onEvent: (EditorUiEvent) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp)
    ) {
        if (editMode) {
            EditModeBottomBar(
                memeTextState = memeTextState,
                bottomBarState = bottomBarState,
                onEvent = onEvent
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
    Row(
        modifier = Modifier.fillMaxWidth().height(72.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
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
    modifier: Modifier = Modifier,
    memeTextState: MemeTextState,
    bottomBarState: BottomBarState,
    onEvent: (EditorUiEvent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        when (bottomBarState.bottomBarItem) {
            BottomBarState.BottomBarItem.ColorPicker ->
                ColorPickerBanner(
                    onColorClicked = { onEvent(EditorUiEvent.ColorPicked(it)) },
                    currentColor = memeTextState.currentTextColor
                )

            BottomBarState.BottomBarItem.FontFamily ->
                FontFamilyBanner(
                    onFontClicked = { onEvent(EditorUiEvent.FontFamilyPicked(it)) },
                    currentFont = memeTextState.currentFontFamily
                )

            BottomBarState.BottomBarItem.FontSize ->
                FontSizeBanner(
                    startPosition = memeTextState.currentFontSize,
                    onValueChange = { onEvent(EditorUiEvent.FontSizeChanged(it)) }
                )

            BottomBarState.BottomBarItem.Initial -> {}
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(72.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ResetIcon(onClick = { onEvent(EditorUiEvent.ResetEditingClicked) })
            TextEditButtons(
                onFontClicked = { onEvent(EditorUiEvent.FontSizeItemClicked) },
                onFontSizeClicked = { onEvent(EditorUiEvent.FontFamilyItemClicked) },
                onColorClicked = { onEvent(EditorUiEvent.ColorPickedItemClicked) }
            )
            ApplyIcon(onClick = { onEvent(EditorUiEvent.ApplyEditingClicked) })
        }
    }

}

@Composable
private fun TextEditButtons(
    modifier: Modifier = Modifier,
    onFontClicked: () -> Unit,
    onFontSizeClicked: () -> Unit,
    onColorClicked: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // FontButton
        IconButton(
            onClick = onFontClicked
        ) {
            Image(
                painter = painterResource(R.drawable.ic_text_style),
                contentDescription = stringResource(R.string.text_style_button)
            )
        }

        // FontSizeButton
        IconButton(
            onClick = onFontSizeClicked
        ) {
            Image(
                painter = painterResource(R.drawable.ic_text_size),
                contentDescription = stringResource(R.string.text_size_button)
            )
        }

        // ColorPickerButton
        IconButton(
            onClick = onColorClicked
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_color_picker),
                contentDescription = stringResource(R.string.color_picker_button)
            )
        }
    }
}