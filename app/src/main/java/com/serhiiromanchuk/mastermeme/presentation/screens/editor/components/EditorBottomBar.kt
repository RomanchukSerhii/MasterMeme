package com.serhiiromanchuk.mastermeme.presentation.screens.editor.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.core.components.OutlinedButton
import com.serhiiromanchuk.mastermeme.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.state.BottomBarState
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.state.MemeTextState

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
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
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
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ResetIcon(onClick = { onEvent(EditorUiEvent.ResetEditingClicked) })
            TextEditButtons(
                bottomBarState = bottomBarState,
                onFontClicked = { onEvent(EditorUiEvent.FontFamilyItemClicked) },
                onFontSizeClicked = { onEvent(EditorUiEvent.FontSizeItemClicked) },
                onColorClicked = { onEvent(EditorUiEvent.ColorPickedItemClicked) }

            )
            ApplyIcon(onClick = { onEvent(EditorUiEvent.ApplyEditingClicked) })
        }
    }

}

@Composable
private fun TextEditButtons(
    modifier: Modifier = Modifier,
    bottomBarState: BottomBarState,
    onFontClicked: () -> Unit,
    onFontSizeClicked: () -> Unit,
    onColorClicked: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // FontButton
        TextEditButton(
            painter = painterResource(R.drawable.ic_text_style),
            contentDescription = stringResource(R.string.text_style_button),
            isSelected = bottomBarState.bottomBarItem == BottomBarState.BottomBarItem.FontFamily,
            onClick = onFontClicked
        )

        // FontSizeButton
        TextEditButton(
            painter = painterResource(R.drawable.ic_text_size),
            contentDescription = stringResource(R.string.text_size_button),
            isSelected = bottomBarState.bottomBarItem == BottomBarState.BottomBarItem.FontSize,
            onClick = onFontSizeClicked
        )

        // ColorPickerButton
        TextEditButton(
            painter = painterResource(R.drawable.ic_color_picker),
            contentDescription = stringResource(R.string.color_picker_button),
            isSelected = bottomBarState.bottomBarItem == BottomBarState.BottomBarItem.ColorPicker,
            onClick = onColorClicked,
            imageSize = 24.dp
        )
    }
}

@Composable
fun TextEditButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    isSelected: Boolean,
    imageSize: Dp? = null,
    contentDescription: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                interactionSource,
                LocalIndication.current
            ) {
                onClick()
            }
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.surfaceContainerHigh
                } else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = if (imageSize != null) Modifier.size(imageSize) else Modifier,
            painter = painter,
            contentDescription = contentDescription
        )
    }
}