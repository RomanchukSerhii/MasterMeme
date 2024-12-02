package com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling

import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiEvent

sealed interface EditorUiEvent : UiEvent {
    data object AddTextClicked : EditorUiEvent
    data object SaveMemeClicked : EditorUiEvent
}