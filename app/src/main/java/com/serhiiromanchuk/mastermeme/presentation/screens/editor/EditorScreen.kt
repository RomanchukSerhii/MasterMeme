package com.serhiiromanchuk.mastermeme.presentation.screens.editor

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.navigaion.NavigationState
import com.serhiiromanchuk.mastermeme.presentation.core.base.BaseContentLayout
import com.serhiiromanchuk.mastermeme.presentation.core.components.BasicDialog
import com.serhiiromanchuk.mastermeme.presentation.core.components.DialogWithTextField
import com.serhiiromanchuk.mastermeme.presentation.core.components.MemeTopBar
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.components.EditorBottomBar
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.components.EditorBottomSheet
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.components.MemeWithEditingText
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorActionEvent.*
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiState
import com.serhiiromanchuk.mastermeme.utils.Constants

@Composable
fun EditorScreenRoot(
    modifier: Modifier = Modifier,
    navigationState: NavigationState,
    memeResId: Int
) {
    val viewModel: EditorViewModel =
        hiltViewModel<EditorViewModel, EditorViewModel.EditorViewModelFactory> { factory ->
            factory.create(memeResId)
        }

    BaseContentLayout(
        modifier = modifier,
        viewModel = viewModel,
        actionsEventHandler = { context, actionEvent ->
            when (actionEvent) {
                NavigationBack -> navigationState.popBackStack()
                ShowToast -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.editor_toast_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ShareMeme -> {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = Constants.MIME_TYPE
                        putExtra(Intent.EXTRA_STREAM, actionEvent.memeUri)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share Meme"))
                }
            }
        },
        topBar = {
            MemeTopBar(
                title = stringResource(R.string.new_meme),
                onBackClick = { viewModel.onEvent(EditorUiEvent.ShowLeaveDialog(true)) }
            )
        },
        onBackPressed = { viewModel.onEvent(EditorUiEvent.ShowLeaveDialog(true)) },
        bottomBar = { uiState ->
            EditorBottomBar(
                onEvent = viewModel::onEvent,
                memeTextState = uiState.editableTextState,
                bottomBarState = uiState.bottomBarState,
                editMode = uiState.bottomBarState.bottomBarEditMode
            )
        }
    ) { uiState ->
        EditorScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent
        )

        EditorBottomSheet(
            openBottomSheet = uiState.bottomSheetOpened,
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
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        MemeWithEditingText(
            memeResId = uiState.memeResId,
            textStateList = uiState.memeTextList,
            editableTextState = uiState.editableTextState,
            onEvent = onEvent
        )
    }

    if (uiState.showBasicDialog) {
        // LeaveDialog
        BasicDialog(
            headline = stringResource(R.string.leave_dialog_headline),
            supportingText = stringResource(R.string.leave_dialog_supporting_text),
            onDismissRequest = { onEvent(EditorUiEvent.ShowLeaveDialog(false)) },
            onConfirm = { onEvent(EditorUiEvent.LeaveDialogConfirmClicked) },
            cancelButtonName = stringResource(R.string.cancel),
            confirmButtonName = stringResource(R.string.leave)
        )
    }

    if (uiState.showEditTextDialog) {
        // EditTextDialog
        DialogWithTextField(
            headline = stringResource(R.string.text),
            initialText = uiState.editableTextState.text,
            onConfirm = { text -> onEvent(EditorUiEvent.ConfirmEditDialogClicked(text)) },
            onDismissRequest = { onEvent(EditorUiEvent.ShowEditTextDialog(false)) }
        )
    }
}