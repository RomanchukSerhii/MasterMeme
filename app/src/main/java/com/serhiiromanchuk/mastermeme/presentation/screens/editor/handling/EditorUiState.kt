package com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling

import android.graphics.Picture
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiState
import com.serhiiromanchuk.mastermeme.presentation.core.state.MemeTextState
import com.serhiiromanchuk.mastermeme.utils.Constants

@Stable
data class EditorUiState(
    @DrawableRes val memeResId: Int = Constants.UNDEFINED_MEME_RES_ID,
    val editableTextState: MemeTextState = INITIAL_MEME_TEXT_STATE,
    val memeTextList: List<MemeTextState> = listOf(),
    val memePicture: Picture = Picture(),
    val memeUriString: String = "",
    val showBasicDialog: Boolean = false,
    val showEditTextDialog: Boolean = false,
    val bottomBarEditMode: Boolean = true,
    val bottomSheetOpened: Boolean = false
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