package com.serhiiromanchuk.mastermeme.presentation.screens.editor

import com.serhiiromanchuk.mastermeme.presentation.core.base.BaseViewModel
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorActionEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private typealias BaseEditorViewModel = BaseViewModel<EditorUiState, EditorUiEvent, EditorActionEvent>

@HiltViewModel
class EditorViewModel @Inject constructor(): BaseEditorViewModel() {
    override val initialState: EditorUiState
        get() = EditorUiState()

    override fun onEvent(event: EditorUiEvent) {
        when (event) {
            EditorUiEvent.AddTextClicked -> updateAddTextDialogState(true)
            EditorUiEvent.SaveMemeClicked -> TODO()
            is EditorUiEvent.ShowBasicDialog -> updateBasicDialogState(event.isVisible)
            is EditorUiEvent.ShowAddTextDialog -> updateAddTextDialogState(event.isVisible)
            is EditorUiEvent.FontSizeChanged -> updateFontSize(event.fontSize)
        }
    }

    private fun updateFontSize(fontSize: Float) {
        updateState { it.copy(fontSize = fontSize) }
    }

    private fun updateBasicDialogState(isVisible: Boolean) {
        updateState { it.copy(showBasicDialog = isVisible) }
    }

    private fun updateAddTextDialogState(isVisible: Boolean) {
        updateState { it.copy(showAddTextDialog = isVisible) }
    }
}