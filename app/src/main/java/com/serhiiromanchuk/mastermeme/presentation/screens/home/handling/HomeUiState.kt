package com.serhiiromanchuk.mastermeme.presentation.screens.home.handling

import androidx.compose.runtime.Stable
import com.serhiiromanchuk.mastermeme.domain.entity.Meme
import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiState
import com.serhiiromanchuk.mastermeme.presentation.core.utils.DropdownSortItem
import com.serhiiromanchuk.mastermeme.presentation.core.utils.MemeTemplate
import com.serhiiromanchuk.mastermeme.presentation.core.utils.MemeTemplateProvider

@Stable
data class HomeUiState(
    val memes: List<Meme> = emptyList(),
    val bottomSheetState: BottomSheetState = BottomSheetState(),
    val selectedItem: DropdownSortItem = DropdownSortItem.Favourite,
    val isSelectionMode: Boolean = false,
    val showDeleteDialog: Boolean = false,
) : UiState {
    val selectedMemesCounter = memes.count { it.isSelected }

    @Stable
    data class BottomSheetState(
        val memeTemplates: List<MemeTemplate> = MemeTemplateProvider.memesList,
        val isOpened: Boolean = false,
        val isSearchMode: Boolean = false,
        val searchText: String = "",
    )
}
