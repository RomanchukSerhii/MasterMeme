package com.serhiiromanchuk.mastermeme.presentation.screens.editor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.core.components.OutlinedText
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.state.MemeTextState
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditingText(
    modifier: Modifier = Modifier,
    memeTextState: MemeTextState,
    onEvent: (EditorUiEvent) -> Unit
) {
    if (memeTextState.isVisible) {
        EditingBox(
            modifier = modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (!memeTextState.isEditMode) {
                                onEvent(EditorUiEvent.EditTextClicked(memeTextState.id))
                            } else {
                                onEvent(EditorUiEvent.ShowEditTextDialog(true))
                            }
                        }
                    )
                },
            isEditMode = memeTextState.isEditMode,
            onDeleteClick = { onEvent(EditorUiEvent.DeleteEditTextClicked(memeTextState.id)) },
            boxSizeDetermined = { width, height ->
                onEvent(EditorUiEvent.EditingBoxSizeDetermined(width, height))
            },
            iconHeightDetermined = { onEvent(EditorUiEvent.EditingIconHeightDetermined(it)) }
        ) {
            OutlinedText(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .onGloballyPositioned { coordinates ->
                        val textHeight = coordinates.size.height.toFloat()
                        onEvent(EditorUiEvent.EditingTextHeightDetermined(textHeight))
                    },
                text = memeTextState.text.trim().uppercase(),
                fontSize = memeTextState.currentFontSize.sp
            )
        }
    }
}

@Composable
private fun EditingBox(
    modifier: Modifier = Modifier,
    isEditMode: Boolean,
    onDeleteClick: () -> Unit,
    boxSizeDetermined: (width: Float, height: Float) -> Unit,
    iconHeightDetermined: (height: Float) -> Unit,
    content: @Composable () -> Unit
) {
    var boxWidth by rememberSaveable { mutableFloatStateOf(0f) }
    var boxHeight by rememberSaveable { mutableFloatStateOf(0f) }

    LaunchedEffect(boxWidth) {
        boxSizeDetermined(boxWidth, boxHeight)
    }
    Box(modifier = modifier) {
        if (isEditMode) {
            Box(
                modifier = Modifier
                    .padding(top = 10.dp, end = 10.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(4.dp))
                    .background(Color.Transparent, RoundedCornerShape(4.dp))
                    .onGloballyPositioned { coordinates ->
                        boxWidth = coordinates.size.width.toFloat()
                        boxHeight = coordinates.size.height.toFloat()
                    }
            ) {
                content()
            }
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable { onDeleteClick() }
                    .onGloballyPositioned { coordinates ->
                        iconHeightDetermined(coordinates.size.height.toFloat())
                    },
                painter = painterResource(R.drawable.ic_delete_edit),
                contentDescription = stringResource(R.string.delete_text),
                tint = Color.Unspecified
            )
        } else {
            content()
        }
    }
}