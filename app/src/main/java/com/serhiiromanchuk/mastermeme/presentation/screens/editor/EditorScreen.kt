package com.serhiiromanchuk.mastermeme.presentation.screens.editor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.core.base.BaseContentLayout
import com.serhiiromanchuk.mastermeme.presentation.core.components.BasicDialog
import com.serhiiromanchuk.mastermeme.presentation.core.components.DialogWithTextField
import com.serhiiromanchuk.mastermeme.presentation.core.components.MemeTopBar
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.components.EditingText
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.components.EditorBottomBar
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.components.FullScreenScrollableImage
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiState

@Composable
fun EditorScreenRoot(modifier: Modifier = Modifier) {
    val viewModel: EditorViewModel = hiltViewModel()

    BaseContentLayout(
        modifier = modifier,
        viewModel = viewModel,
        topBar = {
            MemeTopBar(
                title = stringResource(R.string.new_meme),
                onBackClick = { viewModel.onEvent(EditorUiEvent.ShowBasicDialog(true)) }
            )
        },
        bottomBar = { uiState ->
            EditorBottomBar(
                onEvent = viewModel::onEvent,
                memeTextState = uiState.editableMemeTextState,
                editMode = uiState.bottomSheetEditMode
            )
        }
    ) { uiState ->
        EditorScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun EditorScreen(
    uiState: EditorUiState,
    onEvent: (EditorUiEvent) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        FullScreenScrollableImage(image = painterResource(R.drawable.otri4_40))

        LazyColumn {
            item {
                EditingText(
                    modifier = Modifier.padding(vertical = 12.dp),
                    memeTextState = uiState.editableMemeTextState,
                    onClick = { onEvent(EditorUiEvent.EditTextClicked(it)) },
                    onDoubleClick = { onEvent(EditorUiEvent.EditTextDoubleClicked(it)) },
                    onDeleteClick = { onEvent(EditorUiEvent.DeleteEditTextClicked(it)) },
                )
                Spacer(modifier = Modifier.padding(bottom = 24.dp))
            }

            items(items = uiState.memeTextList, key = { it.id }) { memeTextState ->
                EditingText(
                    modifier = Modifier.padding(vertical = 12.dp),
                    memeTextState = memeTextState,
                    onClick = { onEvent(EditorUiEvent.EditTextClicked(it)) },
                    onDoubleClick = { onEvent(EditorUiEvent.EditTextDoubleClicked(it)) },
                    onDeleteClick = { onEvent(EditorUiEvent.DeleteEditTextClicked(it)) },
                )
            }
        }
    }

    if (uiState.showBasicDialog) {
        // LeaveDialog
        BasicDialog(
            headline = stringResource(R.string.leave_dialog_headline),
            supportingText = stringResource(R.string.leave_dialog_supporting_text),
            onDismissRequest = { onEvent(EditorUiEvent.ShowBasicDialog(false)) },
            onConfirm = { onEvent(EditorUiEvent.ShowBasicDialog(false)) },
            cancelButtonName = stringResource(R.string.cancel),
            confirmButtonName = stringResource(R.string.leave)
        )
    }

    if (uiState.showEditTextDialog) {
        // EditTextDialog
        DialogWithTextField(
            headline = stringResource(R.string.text),
            initialText = uiState.editableMemeTextState.text,
            onConfirm = { text -> onEvent(EditorUiEvent.ConfirmEditDialogClicked(text)) },
            onDismissRequest = { onEvent(EditorUiEvent.ShowEditTextDialog(false)) }
        )
    }
}