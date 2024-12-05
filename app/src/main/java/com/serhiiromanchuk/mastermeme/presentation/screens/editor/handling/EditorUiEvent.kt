package com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling

import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiEvent

sealed interface EditorUiEvent : UiEvent {

    data object AddTextClicked : EditorUiEvent

    data object SaveMemeClicked : EditorUiEvent

    data object ResetEditingClicked : EditorUiEvent

    data object ApplyEditingClicked : EditorUiEvent

    data object BottomSheetModeChanged : EditorUiEvent

    data class ShowBasicDialog(val isVisible: Boolean) : EditorUiEvent

    data class ShowAddTextDialog(val isVisible: Boolean) : EditorUiEvent

    data class FontSizeChanged(val fontSize: Float) : EditorUiEvent
}