@file:OptIn(ExperimentalFoundationApi::class)

package com.serhiiromanchuk.mastermeme.presentation.screens.editor.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.gestures.rememberDraggable2DState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.round
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.core.state.MemeTextState
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent

@Composable
fun MemeWithEditingText(
    textState: MemeTextState,
    onEvent: (EditorUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        FullScreenScrollableImage(
            modifier = Modifier
                .align(Alignment.Center)
                .onGloballyPositioned { coordinates ->
                    onEvent(
                        EditorUiEvent.MemeImageSizeDetermined(
                            width = coordinates.size.width.toFloat(),
                            height = coordinates.size.height.toFloat()
                        )
                    )
                },
            image = painterResource(R.drawable.otri4_40)
        )

        EditingText(
            modifier = Modifier
                .offset {
                    if (textState.isInitialPosition) {
                        textState.initialTextOffset.round()
                    } else {
                        textState.offset.round()
                    }
                }
                .draggable2D(
                    state = rememberDraggable2DState { delta ->
                        val newOffset = textState.offset + delta
                        val minValueY = 0f - textState.dimensions.deleteIconHeight / 2

                        // Limit text movement within specified limits
                        onEvent(
                            EditorUiEvent.EditTextOffsetChanged(
                                offset = Offset(
                                    x = newOffset.x.coerceIn(0f, textState.dimensions.widthBound),
                                    y = newOffset.y.coerceIn(minValueY, textState.dimensions.heightBound)
                                )
                            )
                        )
                    }
                ),
            memeTextState = textState,
            onEvent = onEvent
        )
    }
}