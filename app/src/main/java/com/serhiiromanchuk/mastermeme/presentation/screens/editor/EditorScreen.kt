package com.serhiiromanchuk.mastermeme.presentation.screens.editor

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.core.base.BaseContentLayout
import com.serhiiromanchuk.mastermeme.presentation.core.components.MemeTopBar
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
                onBackClick = {}
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

}