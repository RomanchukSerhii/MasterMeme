package com.serhiiromanchuk.mastermeme.presentation.screens.editor

import androidx.compose.ui.geometry.Offset
import com.serhiiromanchuk.mastermeme.presentation.core.base.BaseViewModel
import com.serhiiromanchuk.mastermeme.presentation.core.state.MemeTextState
import com.serhiiromanchuk.mastermeme.presentation.core.utils.Constants
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
            is ResetEditingClicked -> resetEditableText()
            is ApplyEditingClicked -> saveEditableText()
            is EditTextClicked -> showEditableMemeTextState(getMemeTextState(event.memeId))
            is EditTextOffsetChanged -> updateEditableTextOffset(event.offset)
            is DeleteEditTextClicked -> deleteMemeText(event.memeId)
            is EditingBoxSizeDetermined -> {
                updateEditableTextDimensions {
                    copy(boxWidth = event.width, boxHeight = event.height)
                }
            }

            is EditingIconHeightDetermined -> updateEditableTextDimensions {
                copy(deleteIconHeight = event.height)
            }

            is EditingTextHeightDetermined -> updateEditableTextDimensions {
                copy(textHeight = event.height)
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
                editableTextState = it.editableTextState.copy(currentFontSize = fontSize)
            )
        }
    }

    private fun updateEditableText(text: String) {
        if (currentState.editableTextState.id < 0) {
            addNewEditableText(text)
        } else {
            closeEditTextDialog(currentState.editableTextState.copy(text = text))
        }
    }

    private fun resetEditableText() {
        val updatedMeme = currentState.editableTextState.let {
            it.copy(
                currentFontSize = it.initialFontSize,
                offset = it.initialOffset
            )
        }
        if (currentState.memeTextList.isNotEmpty()) {
            hideEditableText(updatedMeme)
        } else {
            updateState {
                it.copy(
                    editableTextState = MemeTextState(
                        id = Constants.UNDEFINED_MEME_TEXT_STATE_ID,
                        text = "",
                        isVisible = false,
                        isEditMode = true
                    ),
                    bottomSheetEditMode = false
                )
            }
        }
    }

    private fun saveEditableText() {
        val updatedMeme = currentState.editableTextState.let {
            it.copy(
                id = if (it.id < 0) 0 else it.id,
                initialFontSize = it.currentFontSize,
                initialOffset = it.offset
            )
        }

        hideEditableText(updatedMeme)
    }

    private fun showEditableMemeTextState(memeTextState: MemeTextState) {
        if (currentState.editableTextState.isEditMode) saveEditableText()
        val updatedList = replaceMemeTextState(memeTextState.copy(isVisible = false))

        updateState {
            it.copy(
                memeTextList = updatedList,
                bottomSheetEditMode = true,
                editableTextState = memeTextState.copy(
                    isEditMode = true,
                    offset = memeTextState.editModeOffset
                )
            )
        }
    }

    private fun updateEditableTextOffset(offset: Offset) {
        updateState {
            val isInitial = it.editableTextState.isInitialPosition
            val newOffset =
                if (isInitial) it.editableTextState.middlePositionTextOffset + offset else offset
            it.copy(
                editableTextState = it.editableTextState.copy(
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
                editableTextState = MemeTextState()
            )
        }
    }

    private fun updateEditableTextDimensions(update: MemeTextState.Dimensions.() -> MemeTextState.Dimensions) {
        val dimensions = currentState.editableTextState.dimensions.update()
        updateState {
            it.copy(editableTextState = it.editableTextState.copy(dimensions = dimensions))
        }
    }

    private fun addNewEditableText(text: String) {
        val newMemeId = if (currentState.memeTextList.isEmpty()) 0 else {
            currentState.memeTextList.last().id.plus(1)
        }

        closeEditTextDialog(
            editedMemeTextState = MemeTextState(
                id = newMemeId,
                text = text,
                isVisible = true,
                isEditMode = true
            )
        )
    }

    private fun hideEditableText(editedMemeTextState: MemeTextState) {
        val updatedList = replaceMemeTextState(
            editedMemeTextState.copy(
                isEditMode = false,
                isVisible = true,
                offset = editedMemeTextState.editModeOffset
            )
        )
        updateState {
            it.copy(
                memeTextList = updatedList,
                bottomSheetEditMode = false,
                editableTextState = MemeTextState()
            )
        }
    }

    private fun getMemeTextState(memeId: Int): MemeTextState {
        return currentState.memeTextList.find { it.id == memeId } ?: MemeTextState()
    }

    private fun replaceMemeTextState(memeTextState: MemeTextState): List<MemeTextState> {
        val isNewMeme = currentState.memeTextList.none { it.id == memeTextState.id }
        return if (isNewMeme) {
            currentState.memeTextList + memeTextState
        } else {
            currentState.memeTextList.map {
                if (it.id == memeTextState.id) memeTextState else it
            }
        }
    }

    private fun closeEditTextDialog(
        editedMemeTextState: MemeTextState
    ) {
        updateState {
            it.copy(
                editableTextState = editedMemeTextState,
                showEditTextDialog = false,
                bottomSheetEditMode = true
            )
        }
    }
}