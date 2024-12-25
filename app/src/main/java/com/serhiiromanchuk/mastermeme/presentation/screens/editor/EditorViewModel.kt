package com.serhiiromanchuk.mastermeme.presentation.screens.editor

import androidx.compose.ui.geometry.Offset
import com.serhiiromanchuk.mastermeme.presentation.core.base.BaseViewModel
import com.serhiiromanchuk.mastermeme.presentation.core.state.MemeTextState
import com.serhiiromanchuk.mastermeme.presentation.core.utils.Constants
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorActionEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.ApplyEditingClicked
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.BottomSheetModeChanged
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.ConfirmEditDialogClicked
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.DeleteEditTextClicked
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.EditTextClicked
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.EditTextOffsetChanged
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.EditingBoxSizeDetermined
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.EditingIconHeightDetermined
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.EditingTextHeightDetermined
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.FontSizeChanged
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.LeaveDialogConfirmClicked
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.MemeImageSizeDetermined
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.ResetEditingClicked
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.SaveMemeClicked
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.ShowEditTextDialog
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent.ShowLeaveDialog
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

private typealias BaseEditorViewModel = BaseViewModel<EditorUiState, EditorUiEvent, EditorActionEvent>

@HiltViewModel(assistedFactory = EditorViewModel.EditorViewModelFactory::class)
class EditorViewModel @AssistedInject constructor(
    @Assisted val memeResId: Int
) : BaseEditorViewModel() {
    override val initialState: EditorUiState
        get() = EditorUiState()

    init {
        updateState { it.copy(memeResId = memeResId) }
    }

    override fun onEvent(event: EditorUiEvent) {
        when (event) {
            SaveMemeClicked -> handleSaveMemeClicked()
            BottomSheetModeChanged -> toggleBottomSheetMode()
            is ShowLeaveDialog -> updateDialogVisibility { copy(showBasicDialog = event.isVisible) }
            is ShowEditTextDialog -> updateDialogVisibility { copy(showEditTextDialog = event.isVisible) }
            is FontSizeChanged -> updateFontSize(event.fontSize)
            is ConfirmEditDialogClicked -> updateEditableText(event.text)
            is ResetEditingClicked -> resetEditableText()
            is ApplyEditingClicked -> saveEditableText()
            is EditTextClicked -> showEditableMemeTextState(getMemeTextState(event.memeId))
            is EditTextOffsetChanged -> updateEditableTextOffset(event.offset)
            is DeleteEditTextClicked -> deleteMemeText(event.memeId)
            is EditingBoxSizeDetermined -> updateEditingBoxSizeDimensions(
                width = event.width,
                height = event.height
            )

            is EditingIconHeightDetermined -> updateEditableTextDimensions {
                copy(deleteIconHeight = event.height)
            }

            is EditingTextHeightDetermined -> updateEditableTextDimensions {
                copy(textHeight = event.height)
            }

            is MemeImageSizeDetermined -> updateEditableTextDimensions {
                copy(parentImageWidth = event.width, parentImageHeight = event.height)
            }

            LeaveDialogConfirmClicked -> {
                updateDialogVisibility { copy(showBasicDialog = false) }
                sendActionEvent(EditorActionEvent.NavigationBack)
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
        val updatedEditableTextState = currentState.editableTextState.let {
            it.copy(
                currentFontSize = it.initialFontSize,
                offset = it.initialOffset
            )
        }
        if (!isNewMeme(updatedEditableTextState)) {
            hideEditableText(updatedEditableTextState)
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

    private fun showEditableMemeTextState(textState: MemeTextState) {
        if (currentState.editableTextState.isEditMode) saveEditableText()
        val updatedList = replaceTextState(textState.copy(isVisible = false))

        updateState {
            it.copy(
                memeTextList = updatedList,
                bottomSheetEditMode = true,
                editableTextState = textState.copy(
                    isEditMode = true,
                    offset = textState.editModeOffset
                )
            )
        }
    }

    private fun updateEditableTextOffset(offset: Offset) {
        updateState {
            it.copy(
                editableTextState = it.editableTextState.copy(
                    isInitialPosition = false,
                    offset = offset
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

    private fun updateEditingBoxSizeDimensions(width: Float, height: Float) {
        val updatedTextState = currentState.editableTextState.copy(
            dimensions = currentState.editableTextState.dimensions.copy(
                boxWidth = width,
                boxHeight = height
            )
        )

        val boxOffset = if (updatedTextState.isInitialPosition) {
            updatedTextState.middlePositionTextOffset
        } else {
            updatedTextState.ensureWithinBoundsOffset
        }

        updateState {
            it.copy(editableTextState = updatedTextState.copy(offset = boxOffset))
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

    private fun hideEditableText(editedTextState: MemeTextState) {
        val updatedTextState = editedTextState.copy(
            isEditMode = false,
            isVisible = true,
            offset = editedTextState.editModeOffset
        )

        val updatedList = if (isNewMeme(editedTextState)) {
            addTextState(updatedTextState)
        } else {
            replaceTextState(updatedTextState)
        }

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

    private fun addTextState(textState: MemeTextState) = currentState.memeTextList + textState

    private fun replaceTextState(textState: MemeTextState): List<MemeTextState> {
        return currentState.memeTextList.map {
            if (it.id == textState.id) textState else it
        }
    }

    private fun isNewMeme(textState: MemeTextState): Boolean {
        return currentState.memeTextList.none { it.id == textState.id }
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

    @AssistedFactory
    interface EditorViewModelFactory {
        fun create(memeResId: Int): EditorViewModel
    }
}