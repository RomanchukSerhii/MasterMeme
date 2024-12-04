package com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling

import androidx.annotation.DrawableRes
import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiState

data class EditorUiState(
    @DrawableRes val memeTemplate: Int = -1,
    val showBasicDialog: Boolean = false,
    val showAddTextDialog: Boolean = false,
    val fontSize: Float = 28F
) : UiState