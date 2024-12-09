package com.serhiiromanchuk.mastermeme.presentation.core.state

import androidx.compose.runtime.Stable
import com.serhiiromanchuk.mastermeme.presentation.core.utils.Constants.INITIAL_MEME_FONT_SIZE

@Stable
data class MemeTextState(
    val id: Int = 0,
    val text: String = "Tap twice to edit",
    val isEditMode: Boolean = false,
    val isVisible: Boolean = false,
    val initialFontSize: Float = INITIAL_MEME_FONT_SIZE,
    val currentFontSize: Float = INITIAL_MEME_FONT_SIZE,
)
