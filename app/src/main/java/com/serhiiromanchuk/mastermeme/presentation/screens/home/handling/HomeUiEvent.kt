package com.serhiiromanchuk.mastermeme.presentation.screens.home.handling

import com.serhiiromanchuk.mastermeme.domain.entity.Meme
import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiEvent
import com.serhiiromanchuk.mastermeme.presentation.core.utils.DropdownSortItem

sealed interface HomeUiEvent : UiEvent {

    data object FabClicked : HomeUiEvent

    data object BottomSheetDismissed : HomeUiEvent

    data object SelectionModeToggled : HomeUiEvent

    data object DeleteDialogConfirmed : HomeUiEvent

    data object ShareIconClicked : HomeUiEvent

    data class MemeClicked(val memeResId: Int) : HomeUiEvent

    data class MemeFavouriteToggled(val memeId: Int) : HomeUiEvent

    data class SortOptionClicked(val sortItem: DropdownSortItem) : HomeUiEvent

    data class MemeLongPressed(val selectedMeme: Meme) : HomeUiEvent

    data class MemeSelectionToggled(val meme: Meme) : HomeUiEvent

    data class ShowDeleteDialog(val isVisible: Boolean) : HomeUiEvent
}