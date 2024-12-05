package com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling

import androidx.annotation.DrawableRes
import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiState
import com.serhiiromanchuk.mastermeme.presentation.core.utils.Constants.INITIAL_MEME_FONT_SIZE

data class EditorUiState(
    @DrawableRes val memeTemplate: Int = -1,
    val showBasicDialog: Boolean = false,
    val showAddTextDialog: Boolean = false,
    val fontSize: Float = INITIAL_MEME_FONT_SIZE,
    val bottomSheetEditMode: Boolean = false
) : UiState