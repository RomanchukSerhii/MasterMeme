package com.serhiiromanchuk.mastermeme.presentation.core.state

import androidx.compose.runtime.Stable
import com.serhiiromanchuk.mastermeme.presentation.core.utils.Constants.INITIAL_MEME_FONT_SIZE
import com.serhiiromanchuk.mastermeme.presentation.core.utils.Constants.UNDEFINED_MEME_TEXT_STATE

@Stable
data class MemeTextState(
    val id: Int = UNDEFINED_MEME_TEXT_STATE,
    val text: String = "",
    val isEditMode: Boolean = false,
    val isVisible: Boolean = false,
    val offsetX: Float = 0f,
    val offsetY: Float = 0f,
    val initialFontSize: Float = INITIAL_MEME_FONT_SIZE,
    val currentFontSize: Float = INITIAL_MEME_FONT_SIZE,
)
