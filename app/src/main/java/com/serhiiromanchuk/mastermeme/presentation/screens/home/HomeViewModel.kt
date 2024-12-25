package com.serhiiromanchuk.mastermeme.presentation.screens.home

import com.serhiiromanchuk.mastermeme.presentation.core.base.BaseViewModel
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeActionEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private typealias BaseHomeViewModel = BaseViewModel<HomeUiState, HomeUiEvent, HomeActionEvent>

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseHomeViewModel() {
    override val initialState: HomeUiState
        get() = HomeUiState()

    override fun onEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.FabClicked -> updateBottomSheetState(true)
            HomeUiEvent.BottomSheetDismissed -> updateBottomSheetState(false)
            is HomeUiEvent.OnMemeClicked -> {
                updateBottomSheetState(false)
                sendActionEvent(HomeActionEvent.NavigateToEditor(event.memeResId))
            }
        }
    }

    private fun updateBottomSheetState(openBottomSheet: Boolean) {
        updateState { it.copy(bottomSheetOpened = openBottomSheet) }
    }
}