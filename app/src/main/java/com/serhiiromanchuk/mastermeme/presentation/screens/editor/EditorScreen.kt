package com.serhiiromanchuk.mastermeme.presentation.screens.editor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                fontSize = uiState.fontSize,
                editMode = true
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
        contentAlignment = Alignment.Center
    ) {
        FullScreenScrollableImage(image = painterResource(R.drawable.otri4_40))
        EditingText(
            text = "TAP TWICE TO EDIT",
            fontSize = uiState.fontSize.sp
        )
    }

    if (uiState.showBasicDialog) {
        BasicDialog(
            headline = stringResource(R.string.leave_dialog_headline),
            supportingText = stringResource(R.string.leave_dialog_supporting_text),
            onDismissRequest = { onEvent(EditorUiEvent.ShowBasicDialog(false)) },
            onConfirm = { onEvent(EditorUiEvent.ShowBasicDialog(false)) },
            cancelButtonName = stringResource(R.string.cancel),
            confirmButtonName = stringResource(R.string.leave)
        )
    }

    if (uiState.showAddTextDialog) {
        DialogWithTextField(
            headline = stringResource(R.string.text),
            initialText = "Tap twice to edit",
            onConfirm = { onEvent(EditorUiEvent.ShowAddTextDialog(false)) },
            onDismissRequest = { onEvent(EditorUiEvent.ShowAddTextDialog(false)) }
        )
    }
}