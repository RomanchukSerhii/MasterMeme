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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.core.components.OutlinedText
import com.serhiiromanchuk.mastermeme.presentation.core.state.MemeTextState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditingText(
    modifier: Modifier = Modifier,
    memeTextState: MemeTextState,
    onClick: (memeId: Int) -> Unit,
    onDoubleClick: (memeId: Int) -> Unit,
    onDeleteClick: (memeId: Int) -> Unit
) {
    if (memeTextState.isVisible) {
        EditingBox(
            modifier = modifier,
            isEditMode = memeTextState.isEditMode,
            onClick = {
                if (!memeTextState.isEditMode) onClick(memeTextState.id)
            },
            onDoubleClick = {
                if (memeTextState.isEditMode) onDoubleClick(memeTextState.id)
            },
            onDeleteClick = { onDeleteClick(memeTextState.id) }
        ) {
            OutlinedText(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
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
    onClick: () -> Unit,
    onDoubleClick: () -> Unit,
    onDeleteClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clickable {
                onClick()
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        onDoubleClick()
                    },
                    onTap = {
                        onClick()
                    }
                )
            }
    ) {
        if (isEditMode) {
            Box(
                modifier = Modifier
                    .padding(top = 10.dp, end = 10.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(4.dp))
                    .background(Color.Transparent, RoundedCornerShape(4.dp))
            ) {
                content()
            }
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable { onDeleteClick() },
                painter = painterResource(R.drawable.ic_delete_edit),
                contentDescription = stringResource(R.string.delete_text),
                tint = Color.Unspecified
            )
        } else {
            content()
        }
    }
}