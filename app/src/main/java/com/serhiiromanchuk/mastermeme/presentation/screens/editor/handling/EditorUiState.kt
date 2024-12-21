package com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling

import androidx.annotation.DrawableRes
import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiState
import com.serhiiromanchuk.mastermeme.presentation.core.state.MemeTextState

data class EditorUiState(
    @DrawableRes val memeTemplate: Int = -1,
    val editableMemeTextState: MemeTextState = INITIAL_MEME_TEXT_STATE,
    val memeTextList: List<MemeTextState> = INITIAL_MEME_TEXT_LIST,
    val showBasicDialog: Boolean = false,
    val showEditTextDialog: Boolean = false,
    val bottomSheetEditMode: Boolean = true
) : UiState {
    companion object {
        private val INITIAL_MEME_TEXT_STATE = MemeTextState(
            id = 0,
            text = "Tap twice to edit",
            isVisible = true,
            isEditMode = true
        )
        private val INITIAL_MEME_TEXT_LIST = listOf(
            INITIAL_MEME_TEXT_STATE.copy(
                isEditMode = false,
                isVisible = false
            )
        )
    }
}