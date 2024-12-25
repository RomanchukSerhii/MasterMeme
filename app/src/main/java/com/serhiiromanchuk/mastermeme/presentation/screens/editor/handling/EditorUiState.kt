package com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling

import androidx.annotation.DrawableRes
import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiState
import com.serhiiromanchuk.mastermeme.presentation.core.state.MemeTextState
import com.serhiiromanchuk.mastermeme.presentation.core.utils.Constants

data class EditorUiState(
    @DrawableRes val memeResId: Int = Constants.UNDEFINED_MEME_RES_ID,
    val editableTextState: MemeTextState = INITIAL_MEME_TEXT_STATE,
    val memeTextList: List<MemeTextState> = listOf(),
    val showBasicDialog: Boolean = false,
    val showEditTextDialog: Boolean = false,
    val bottomSheetEditMode: Boolean = true
) : UiState {
    companion object {
        private val INITIAL_MEME_TEXT_STATE = MemeTextState(
            id = Constants.UNDEFINED_MEME_TEXT_STATE_ID,
            text = "Tap twice to edit",
            isVisible = true,
            isEditMode = true
        )
    }
}