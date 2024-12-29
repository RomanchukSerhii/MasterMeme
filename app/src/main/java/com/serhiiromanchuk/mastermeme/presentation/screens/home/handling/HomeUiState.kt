package com.serhiiromanchuk.mastermeme.presentation.screens.home.handling

import com.serhiiromanchuk.mastermeme.domain.entity.Meme
import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiState
import com.serhiiromanchuk.mastermeme.presentation.core.utils.TopBarSortItem

data class HomeUiState(
    val memes: List<Meme> = emptyList(),
    val bottomSheetOpened: Boolean = false,
    val selectedItem: TopBarSortItem = TopBarSortItem.Favourite
) : UiState
