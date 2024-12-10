package com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling

import androidx.annotation.DrawableRes
import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiState
import com.serhiiromanchuk.mastermeme.presentation.core.state.MemeTextState

data class EditorUiState(
    @DrawableRes val memeTemplate: Int = -1,
    val editableMemeTextState: MemeTextState = MemeTextState(
        id = 0,
        text = "Tap twice to edit",
        isVisible = true,
        isEditMode = true
    ),
    val memeTextList: List<MemeTextState> = listOf(
        editableMemeTextState.copy(
            isEditMode = false,
            isVisible = false
        )
    ),
    val showBasicDialog: Boolean = false,
    val showEditTextDialog: Boolean = false,
    val bottomSheetEditMode: Boolean = true
) : UiState