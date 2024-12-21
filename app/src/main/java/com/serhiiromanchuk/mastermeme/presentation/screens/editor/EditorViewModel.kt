package com.serhiiromanchuk.mastermeme.presentation.screens.editor

import androidx.compose.ui.geometry.Offset
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
            SaveMemeClicked -> handleSaveMemeClicked()
            BottomSheetModeChanged -> toggleBottomSheetMode()
            is ShowBasicDialog -> updateDialogVisibility { copy(showBasicDialog = event.isVisible) }
            is ShowEditTextDialog -> updateDialogVisibility { copy(showEditTextDialog = event.isVisible) }
            is FontSizeChanged -> updateFontSize(event.fontSize)
            is ConfirmEditDialogClicked -> updateEditableText(event.text)
            is ResetEditingClicked -> saveEditableText(isFontReset = true)
            is ApplyEditingClicked -> saveEditableText(isFontReset = false)
            is EditTextClicked -> showEditableMemeTextState(getMemeTextState(event.memeId))
            is EditTextOffsetChanged -> updateEditableTextOffset(event.offset)
            is DeleteEditTextClicked -> deleteMemeText(event.memeId)
            is EditingBoxSizeDetermined -> updateEditableTextDimensions {
                copy(boxWidth = event.width, boxHeight = event.height)
            }
            is EditingIconHeightDetermined -> updateEditableTextDimensions {
                copy(deleteIconHeight = event.height)
            }
            is MemeImageSizeDetermined -> updateEditableTextDimensions {
                copy(parentImageWidth = event.width, parentImageHeight = event.height)
            }
        }
    }

    private fun handleSaveMemeClicked() {
        // TODO: Implement saving logic
    }

    private fun toggleBottomSheetMode() {
        updateState { it.copy(bottomSheetEditMode = !it.bottomSheetEditMode) }
    }

    private fun updateDialogVisibility(update: EditorUiState.() -> EditorUiState) {
        updateState { update(it) }
    }

    private fun updateFontSize(fontSize: Float) {
        updateState {
            it.copy(
                editableMemeTextState = it.editableMemeTextState.copy(currentFontSize = fontSize)
            )
        }
    }

    private fun updateEditableText(text: String) {
        if (currentState.editableMemeTextState.id < 0) {
            addNewEditableText(text)
        } else {
            changeCurrentEditableText(text)
        }
    }

    private fun saveEditableText(isFontReset: Boolean) {
        val updatedMeme = currentState.editableMemeTextState.let {
            if (isFontReset) it.copy(currentFontSize = it.initialFontSize)
            else it.copy(initialFontSize = it.currentFontSize)
        }

        hideEditableText(updatedMeme)
    }

    private fun showEditableMemeTextState(memeTextState: MemeTextState) {
        saveEditableText(isFontReset = false)
        val updatedList = replaceMemeTextState(memeTextState.copy(isVisible = false))

        updateState {
            it.copy(
                memeTextList = updatedList,
                bottomSheetEditMode = true,
                editableMemeTextState = memeTextState.copy(isEditMode = true)
            )
        }
    }

    private fun updateEditableTextOffset(offset: Offset) {
        updateState {
            val isInitial = it.editableMemeTextState.isInitialPosition
            val newOffset = if (isInitial) it.editableMemeTextState.initialTextOffset + offset else offset
            it.copy(
                editableMemeTextState = it.editableMemeTextState.copy(
                    isInitialPosition = false,
                    offset = newOffset
                )
            )
        }
    }

    private fun deleteMemeText(memeId: Int) {
        val updatedList = currentState.memeTextList.filterNot { it.id == memeId }
        updateState {
            it.copy(
                memeTextList = updatedList,
                bottomSheetEditMode = false,
                editableMemeTextState = MemeTextState()
            )
        }
    }

    private fun updateEditableTextDimensions(update: MemeTextState.Dimensions.() -> MemeTextState.Dimensions) {
        val dimensions = currentState.editableMemeTextState.dimensions.update()
        updateState {
            it.copy(editableMemeTextState = it.editableMemeTextState.copy(dimensions = dimensions))
        }
    }

    private fun addNewEditableText(text: String) {
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

    private fun changeCurrentEditableText(text: String) {
        val editedMemeTextState = currentState.editableMemeTextState.copy(text = text)
        val updatedList = replaceMemeTextState(
            editedMemeTextState.copy(isVisible = false, isEditMode = false)
        )

        closeEditTextDialog(updatedList, editedMemeTextState)
    }

    private fun hideEditableText(editedMemeTextState: MemeTextState) {
        val updatedList = replaceMemeTextState(
            editedMemeTextState.copy(isEditMode = false, isVisible = true)
        )
        updateState {
            it.copy(
                memeTextList = updatedList,
                bottomSheetEditMode = false,
                editableMemeTextState = MemeTextState()
            )
        }
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