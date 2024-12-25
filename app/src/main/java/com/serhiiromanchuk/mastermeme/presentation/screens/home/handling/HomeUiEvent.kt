package com.serhiiromanchuk.mastermeme.presentation.screens.home.handling

import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiEvent

sealed interface HomeUiEvent : UiEvent {

    data object FabClicked : HomeUiEvent

    data object BottomSheetDismissed : HomeUiEvent

    data class OnMemeClicked(val memeResId: Int) : HomeUiEvent
}