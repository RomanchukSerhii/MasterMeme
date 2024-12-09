package com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling

import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiEvent

sealed interface EditorUiEvent : UiEvent {

    data object SaveMemeClicked : EditorUiEvent

    data object BottomSheetModeChanged : EditorUiEvent

    data class ShowBasicDialog(val isVisible: Boolean) : EditorUiEvent

    data class ShowEditTextDialog(val isVisible: Boolean) : EditorUiEvent

    data class FontSizeChanged(val fontSize: Float) : EditorUiEvent

    data class ConfirmEditDialogClicked(val text: String) : EditorUiEvent

    data class ResetEditingClicked(val memeId: Int) : EditorUiEvent

    data class ApplyEditingClicked(val memeId: Int) : EditorUiEvent

    data class EditTextClicked(val memeId: Int) : EditorUiEvent

    data class EditTextDoubleClicked(val memeId: Int) : EditorUiEvent

    data class DeleteEditTextClicked(val memeId: Int) : EditorUiEvent
}