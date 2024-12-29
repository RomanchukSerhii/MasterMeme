package com.serhiiromanchuk.mastermeme.presentation.screens.home.handling

import androidx.compose.runtime.Stable
import com.serhiiromanchuk.mastermeme.domain.entity.Meme
import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiState
import com.serhiiromanchuk.mastermeme.presentation.core.utils.DropdownSortItem

@Stable
data class HomeUiState(
    val memes: List<Meme> = emptyList(),
    val bottomSheetOpened: Boolean = false,
    val selectedItem: DropdownSortItem = DropdownSortItem.Favourite,
    val isSelectionMode: Boolean = false,
    val showDeleteDialog: Boolean = false
) : UiState {
    val selectedMemesCounter = memes.count { it.isSelected }
}
