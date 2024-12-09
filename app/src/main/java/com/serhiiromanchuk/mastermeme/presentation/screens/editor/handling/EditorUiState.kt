package com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling

import androidx.annotation.DrawableRes
import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiState
import com.serhiiromanchuk.mastermeme.presentation.core.state.MemeTextState

data class EditorUiState(
    @DrawableRes val memeTemplate: Int = -1,
    val memeTextList: List<MemeTextState> = listOf(),
    val editableMemeTextState: MemeTextState = MemeTextState(),
    val showBasicDialog: Boolean = false,
    val showEditTextDialog: Boolean = false,
    val bottomSheetEditMode: Boolean = false
) : UiState