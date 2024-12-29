package com.serhiiromanchuk.mastermeme.presentation.screens.home.handling

import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiEvent
import com.serhiiromanchuk.mastermeme.presentation.core.utils.TopBarSortItem

sealed interface HomeUiEvent : UiEvent {

    data object FabClicked : HomeUiEvent

    data object BottomSheetDismissed : HomeUiEvent

    data class MemeClicked(val memeResId: Int) : HomeUiEvent

    data class MemeFavouriteToggled(val memeId: Int) : HomeUiEvent

    data class SortOptionClicked(val sortItem: TopBarSortItem) : HomeUiEvent
}