@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.serhiiromanchuk.mastermeme.presentation.screens.editor.components

import android.graphics.Picture
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.gestures.rememberDraggable2DState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.round
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.state.MemeTextState
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent

@Composable
fun MemeWithEditingText(
    @DrawableRes memeResId: Int,
    textStateList: List<MemeTextState>,
    editableTextState: MemeTextState,
    onEvent: (EditorUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .drawWithCache {
            val width = this.size.width.toInt()
            val height = this.size.height.toInt()
            onDrawWithContent {
                if (!editableTextState.isEditMode) {
                    val picture = Picture()
                    val pictureCanvas = Canvas(picture.beginRecording(width, height))
                    draw(this, this.layoutDirection, pictureCanvas, this.size) {
                        this@onDrawWithContent.drawContent()
                    }
                    picture.endRecording()
                    onEvent(EditorUiEvent.PictureSaved(picture))
                }
                drawContent()
            }
        }) {
        FullScreenScrollableImage(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    onEvent(
                        EditorUiEvent.MemeImageSizeDetermined(
                            width = coordinates.size.width.toFloat(),
                            height = coordinates.size.height.toFloat()
                        )
                    )
                },
            image = painterResource(id = memeResId)
        )

        EditingText(
            modifier = Modifier
                .offset {
                    editableTextState.offset.round()
                }
                .draggable2D(
                    state = rememberDraggable2DState { delta ->
                        val newOffset = editableTextState.offset + delta
                        val minValueY = 0f - editableTextState.dimensions.deleteIconHeight / 2

                        // Limit text movement within specified limits
                        onEvent(
                            EditorUiEvent.EditTextOffsetChanged(
                                offset = Offset(
                                    x = newOffset.x.coerceIn(
                                        0f,
                                        editableTextState.dimensions.widthBound
                                    ),
                                    y = newOffset.y.coerceIn(
                                        minValueY,
                                        editableTextState.dimensions.heightBound
                                    )
                                )
                            )
                        )
                    }
                ),
            memeTextState = editableTextState,
            onEvent = onEvent
        )

        textStateList.forEach { textState ->
            EditingText(
                modifier = Modifier
                    .offset {
                        textState.offset.round()
                    },
                memeTextState = textState,
                onEvent = onEvent
            )
        }
    }
}