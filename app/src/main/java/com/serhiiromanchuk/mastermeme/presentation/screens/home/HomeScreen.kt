package com.serhiiromanchuk.mastermeme.presentation.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serhiiromanchuk.mastermeme.presentation.core.base.BaseContentLayout
import com.serhiiromanchuk.mastermeme.presentation.core.components.MasterMemeFAB
import com.serhiiromanchuk.mastermeme.presentation.screens.home.components.EmptyHomeScreen
import com.serhiiromanchuk.mastermeme.presentation.screens.home.components.HomeBottomSheet
import com.serhiiromanchuk.mastermeme.presentation.screens.home.components.HomeTopBar
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiState

@Composable
fun HomeScreenRoot(modifier: Modifier = Modifier) {

    val viewModel: HomeViewModel = hiltViewModel()

    BaseContentLayout(
        modifier = modifier,
        viewModel = viewModel,
        topBar = {
            HomeTopBar(modifier = Modifier.padding(16.dp))
        },
        floatingActionButton = {
            MasterMemeFAB(
                onClick = { viewModel.onEvent(HomeUiEvent.FabClicked) }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { uiState ->
        HomeScreen(uiState)
        HomeBottomSheet(
            openBottomSheet = uiState.bottomSheetOpened,
            onDismiss = { viewModel.onEvent(HomeUiEvent.BottomSheetDismissed) }
        )
    }
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState
) {
    EmptyHomeScreen()
}