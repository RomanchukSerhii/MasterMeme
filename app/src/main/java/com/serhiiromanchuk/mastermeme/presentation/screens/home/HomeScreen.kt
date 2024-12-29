package com.serhiiromanchuk.mastermeme.presentation.screens.home

import android.content.Intent
import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.navigaion.NavigationState
import com.serhiiromanchuk.mastermeme.presentation.core.base.BaseContentLayout
import com.serhiiromanchuk.mastermeme.presentation.core.components.BasicDialog
import com.serhiiromanchuk.mastermeme.presentation.core.components.MasterMemeFAB
import com.serhiiromanchuk.mastermeme.presentation.core.components.MemeTopBar
import com.serhiiromanchuk.mastermeme.presentation.screens.home.components.EmptyHomeScreen
import com.serhiiromanchuk.mastermeme.presentation.screens.home.components.HomeBottomSheet
import com.serhiiromanchuk.mastermeme.presentation.screens.home.components.MemeVerticalGrid
import com.serhiiromanchuk.mastermeme.presentation.screens.home.components.SelectedModeTopBar
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeActionEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiState
import com.serhiiromanchuk.mastermeme.utils.Constants

@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    navigationState: NavigationState
) {

    val viewModel: HomeViewModel = hiltViewModel()

    BaseContentLayout(
        modifier = modifier,
        viewModel = viewModel,
        actionsEventHandler = { context, actionEvent ->
            when (actionEvent) {
                is HomeActionEvent.NavigateToEditor -> navigationState.navigateToEditor(actionEvent.memeResId)
                is HomeActionEvent.ShareMemes -> {
                    val memeUriList = actionEvent.memeUriList

                    val shareIntent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
                        type = Constants.MIME_TYPE
                        putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(memeUriList))
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }

                    context.startActivity(Intent.createChooser(shareIntent, "Share Memes"))
                }
            }
        },
        topBar = { uiState ->
            if (uiState.isSelectionMode) {
                SelectedModeTopBar(
                    selectedItemCounter = uiState.selectedMemesCounter,
                    onEvent = viewModel::onEvent
                )
            } else {
                MemeTopBar(
                    title = stringResource(R.string.your_memes),
                    selectedItem = uiState.selectedItem,
                    onSortOptionSelected = { viewModel.onEvent(HomeUiEvent.SortOptionClicked(it)) }
                )
            }

        },
        floatingActionButton = {
            MasterMemeFAB(
                onClick = { viewModel.onEvent(HomeUiEvent.FabClicked) }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { uiState ->
        HomeScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent
        )
        HomeBottomSheet(
            openBottomSheet = uiState.bottomSheetOpened,
            onEvent = viewModel::onEvent
        )
        if (uiState.showDeleteDialog) {
            BasicDialog(
                headline = stringResource(
                    R.string.delete_dialog_headline,
                    uiState.selectedMemesCounter
                ),
                supportingText = stringResource(R.string.delete_dialog_supporting_text),
                confirmButtonName = stringResource(R.string.delete),
                cancelButtonName = stringResource(R.string.cancel),
                onDismissRequest = { viewModel.onEvent(HomeUiEvent.ShowDeleteDialog(false)) },
                onConfirm = { viewModel.onEvent(HomeUiEvent.DeleteDialogConfirmed) }
            )
        }
    }
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit
) {
    if (uiState.memes.isEmpty()) {
        EmptyHomeScreen()
    } else {
        MemeVerticalGrid(
            memeList = uiState.memes,
            onEvent = onEvent,
            isSelectionMode = uiState.isSelectionMode
        )
    }
}