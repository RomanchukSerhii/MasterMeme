package com.serhiiromanchuk.mastermeme.presentation.screens.home

import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.navigaion.NavigationState
import com.serhiiromanchuk.mastermeme.presentation.core.base.BaseContentLayout
import com.serhiiromanchuk.mastermeme.presentation.core.components.MasterMemeFAB
import com.serhiiromanchuk.mastermeme.presentation.core.components.MemeTopBar
import com.serhiiromanchuk.mastermeme.presentation.screens.home.components.EmptyHomeScreen
import com.serhiiromanchuk.mastermeme.presentation.screens.home.components.HomeBottomSheet
import com.serhiiromanchuk.mastermeme.presentation.screens.home.components.MemeVerticalGrid
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeActionEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiState

@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    navigationState: NavigationState
) {

    val viewModel: HomeViewModel = hiltViewModel()

    BaseContentLayout(
        modifier = modifier,
        viewModel = viewModel,
        actionsEventHandler = { _, actionEvent ->
            when (actionEvent) {
                is HomeActionEvent.NavigateToEditor -> navigationState.navigateToEditor(actionEvent.memeResId)
            }
        },
        topBar = {
            MemeTopBar(
                title = stringResource(R.string.your_memes)
            )
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
            onEvent = onEvent
        )
    }
}