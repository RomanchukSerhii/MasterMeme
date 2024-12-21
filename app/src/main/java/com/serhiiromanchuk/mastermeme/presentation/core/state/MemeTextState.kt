package com.serhiiromanchuk.mastermeme.presentation.core.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import com.serhiiromanchuk.mastermeme.presentation.core.utils.Constants.INITIAL_MEME_FONT_SIZE
import com.serhiiromanchuk.mastermeme.presentation.core.utils.Constants.UNDEFINED_MEME_TEXT_STATE

@Stable
data class MemeTextState(
    val id: Int = UNDEFINED_MEME_TEXT_STATE,
    val text: String = "",
    val isEditMode: Boolean = false,
    val isVisible: Boolean = false,
    val isInitialPosition: Boolean = true,
    val dimensions: Dimensions = Dimensions(),
    val offset: Offset = Offset(0f, 0f),
    val initialFontSize: Float = INITIAL_MEME_FONT_SIZE,
    val currentFontSize: Float = INITIAL_MEME_FONT_SIZE,
) {
    val initialTextOffset = calculateInitialOffset()

    @Stable
    data class Dimensions(
        val boxWidth: Float = 0f,
        val boxHeight: Float = 0f,
        val deleteIconHeight: Float = 0f,
        val parentImageHeight: Float = 0f,
        val parentImageWidth: Float = 0f,
    ) {
        val widthBound = parentImageWidth - boxWidth
        val heightBound = parentImageHeight - boxHeight - deleteIconHeight / 2
    }

    private fun calculateInitialOffset(): Offset {
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
}
