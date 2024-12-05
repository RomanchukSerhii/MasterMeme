package com.serhiiromanchuk.mastermeme.presentation.screens.editor

import com.serhiiromanchuk.mastermeme.presentation.core.base.BaseViewModel
import com.serhiiromanchuk.mastermeme.presentation.core.utils.Constants.INITIAL_MEME_FONT_SIZE
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
            EditorUiEvent.AddTextClicked -> {
                updateBottomSheetMode()
                updateAddTextDialogState(true)
            }
            EditorUiEvent.SaveMemeClicked -> TODO()
            EditorUiEvent.ResetEditingClicked -> resetEditingText()
            EditorUiEvent.ApplyEditingClicked -> updateBottomSheetMode()
            EditorUiEvent.BottomSheetModeChanged -> updateBottomSheetMode()
            is EditorUiEvent.ShowBasicDialog -> updateBasicDialogState(event.isVisible)
            is EditorUiEvent.ShowAddTextDialog -> updateAddTextDialogState(event.isVisible)
            is EditorUiEvent.FontSizeChanged -> updateFontSize(event.fontSize)
        }
    }

    private fun resetEditingText() {
        updateState {
            it.copy(
                fontSize = INITIAL_MEME_FONT_SIZE,
                bottomSheetEditMode = false
            )
        }
    }

    private fun updateBottomSheetMode() {
        updateState { it.copy(bottomSheetEditMode = !it.bottomSheetEditMode) }
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