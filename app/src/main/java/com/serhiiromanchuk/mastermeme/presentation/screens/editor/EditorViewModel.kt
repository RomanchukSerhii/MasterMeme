package com.serhiiromanchuk.mastermeme.presentation.screens.editor

import com.serhiiromanchuk.mastermeme.presentation.core.base.BaseViewModel
import com.serhiiromanchuk.mastermeme.presentation.core.state.MemeTextState
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorActionEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.*
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private typealias BaseEditorViewModel = BaseViewModel<EditorUiState, EditorUiEvent, EditorActionEvent>

@HiltViewModel
class EditorViewModel @Inject constructor() : BaseEditorViewModel() {
    override val initialState: EditorUiState
        get() = EditorUiState()

    override fun onEvent(event: EditorUiEvent) {
        when (event) {

            SaveMemeClicked -> TODO()

            BottomSheetModeChanged -> updateBottomSheetMode()

            is ShowBasicDialog -> updateBasicDialogState(event.isVisible)

            is ShowEditTextDialog -> updateEditTextDialogState(event.isVisible)

            is FontSizeChanged -> updateFontSize(event.fontSize)

            is ConfirmEditDialogClicked -> saveEditingText(event.text)

            is ResetEditingClicked -> saveMemeTextState(isFontReset = true)

            is ApplyEditingClicked -> saveMemeTextState(isFontReset = false)

            is EditTextDoubleClicked -> updateEditTextDialogState(true)

            is EditTextClicked -> showEditableMemeTextState(getMemeTextState(event.memeId))

            is DeleteEditTextClicked -> deleteMemeTextState(event.memeId)
        }
    }

    private fun saveEditingText(text: String) {
        if (currentState.editableMemeTextState.id < 0) {
            addNewEditingText(text)
        } else {
            changeCurrentEditingText(text)
        }
    }

    private fun addNewEditingText(text: String) {
        val newMemeId = if (currentState.memeTextList.isEmpty()) 0 else {
            currentState.memeTextList.last().id.plus(1)
        }
        val newMemeState = MemeTextState(
            id = newMemeId,
            text = text
        )

        closeEditTextDialog(
            updatedList = currentState.memeTextList + newMemeState,
            editedMemeTextState = newMemeState.copy(
                isVisible = true,
                isEditMode = true
            )
        )
    }

    private fun changeCurrentEditingText(text: String) {
        val editedMemeTextState = currentState.editableMemeTextState.copy(text = text)
        val updatedList = replaceMemeTextState(
            editedMemeTextState.copy(isVisible = false, isEditMode = false)
        )

        closeEditTextDialog(updatedList, editedMemeTextState)
    }

    private fun deleteMemeTextState(memeId: Int) {
        val meme = getMemeTextState(memeId)
        val updatedList = currentState.memeTextList - meme
        updateState {
            it.copy(
                memeTextList = updatedList,
                bottomSheetEditMode = false,
                editableMemeTextState = MemeTextState()
            )
        }
    }

    private fun saveMemeTextState(isFontReset: Boolean) {
        val editedMemeTextState = if (isFontReset) {
            currentState.editableMemeTextState.copy(
                currentFontSize = currentState.editableMemeTextState.initialFontSize
            )
        } else {
            currentState.editableMemeTextState.copy(
                initialFontSize = currentState.editableMemeTextState.currentFontSize
            )
        }

        hideEditableMemeTextState(editedMemeTextState)
    }

    private fun hideEditableMemeTextState(editedMemeTextState: MemeTextState) {
        val updatedList = replaceMemeTextState(
            editedMemeTextState.copy(
                isEditMode = false,
                isVisible = true
            )
        )
        updateState {
            it.copy(
                memeTextList = updatedList,
                bottomSheetEditMode = false,
                editableMemeTextState = MemeTextState()
            )
        }
    }

    private fun showEditableMemeTextState(memeTextState: MemeTextState) {
        saveMemeTextState(isFontReset = false)
        val updatedList = replaceMemeTextState(
            memeTextState.copy(
                isVisible = false
            )
        )
        updateState {
            it.copy(
                memeTextList = updatedList,
                bottomSheetEditMode = true,
                editableMemeTextState = memeTextState.copy(
                    isEditMode = true
                )
            )
        }
    }

    private fun updateBottomSheetMode() {
        updateState { it.copy(bottomSheetEditMode = !it.bottomSheetEditMode) }
    }

    private fun updateFontSize(fontSize: Float) {
        updateState {
            it.copy(
                editableMemeTextState = it.editableMemeTextState.copy(currentFontSize = fontSize)
            )
        }
    }

    private fun updateBasicDialogState(isVisible: Boolean) {
        updateState { it.copy(showBasicDialog = isVisible) }
    }

    private fun updateEditTextDialogState(isVisible: Boolean) {
        updateState { it.copy(showEditTextDialog = isVisible) }
    }

    private fun getMemeTextState(memeId: Int): MemeTextState {
        return currentState.memeTextList.find { it.id == memeId } ?: MemeTextState()
    }

    private fun replaceMemeTextState(memeTextState: MemeTextState): List<MemeTextState> {
        return currentState.memeTextList.map {
            if (it.id == memeTextState.id) memeTextState else it
        }
    }

    private fun closeEditTextDialog(
        updatedList: List<MemeTextState>,
        editedMemeTextState: MemeTextState
    ) {
        updateState {
            it.copy(
                memeTextList = updatedList,
                editableMemeTextState = editedMemeTextState,
                showEditTextDialog = false,
                bottomSheetEditMode = true
            )
        }
    }
}