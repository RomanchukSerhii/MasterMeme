package com.serhiiromanchuk.mastermeme.presentation.core.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import com.serhiiromanchuk.mastermeme.presentation.core.utils.Constants

@Stable
data class MemeTextState(
    val id: Int = Constants.UNDEFINED_MEME_TEXT_STATE_ID,
    val text: String = "",
    val isEditMode: Boolean = false,
    val isVisible: Boolean = false,
    val isInitialPosition: Boolean = true,
    val dimensions: Dimensions = Dimensions(),
    val offset: Offset = Offset(0f, 0f),
    val initialOffset: Offset = Offset(0f, 0f),
    val initialFontSize: Float = Constants.INITIAL_MEME_FONT_SIZE,
    val currentFontSize: Float = Constants.INITIAL_MEME_FONT_SIZE,
) {
    val middlePositionTextOffset = calculateMiddlePositionOffset()
    val editModeOffset = calculateEditModeOffset(isEditMode)
    val ensureWithinBoundsOffset = adjustOffsetToBounds()

    @Stable
    data class Dimensions(
        val boxWidth: Float = 0f,
        val boxHeight: Float = 0f,
        val deleteIconHeight: Float = 0f,
        val textHeight: Float = 0f,
        val parentImageHeight: Float = 0f,
        val parentImageWidth: Float = 0f,
    ) {
        val widthBound = parentImageWidth - boxWidth
        val heightBound = parentImageHeight - boxHeight - deleteIconHeight / 2
    }

    private fun calculateEditModeOffset(isEditMode: Boolean): Offset {
        val offsetY = if (isEditMode) {
            offset.y + (dimensions.boxHeight - dimensions.textHeight + 5f)
        } else {
            offset.y - (dimensions.boxHeight - dimensions.textHeight + 5f)
        }
        return Offset(offset.x, offsetY)
    }

    private fun calculateMiddlePositionOffset(): Offset {
        val boxWidth = dimensions.boxWidth
        val boxHeight = dimensions.boxHeight
        val deleteIconHeight = dimensions.deleteIconHeight
        val parentImageHeight = dimensions.parentImageHeight
        val parentImageWidth = dimensions.parentImageWidth

        return Offset(
            x = parentImageWidth / 2 - boxWidth / 2,
            y = parentImageHeight / 2 - (boxHeight + deleteIconHeight) / 2
        )
    }

    private fun adjustOffsetToBounds(): Offset {
        val horizontalOverflow = (offset.x + dimensions.boxWidth) - dimensions.parentImageWidth
        val verticalOverflow = (offset.y + dimensions.boxHeight + dimensions.deleteIconHeight / 2) - dimensions.parentImageHeight

        val adjustedX = if (offset.x + dimensions.boxWidth > dimensions.parentImageWidth) {
            offset.x - horizontalOverflow
        } else {
            offset.x
        }

        val adjustedY = if (offset.y + dimensions.boxHeight + dimensions.deleteIconHeight / 2 > dimensions.parentImageHeight) {
            offset.y - verticalOverflow
        } else {
            offset.y
        }

        return Offset(adjustedX, adjustedY)
    }
}