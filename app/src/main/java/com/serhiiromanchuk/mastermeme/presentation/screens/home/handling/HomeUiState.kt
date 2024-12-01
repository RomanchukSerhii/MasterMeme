package com.serhiiromanchuk.mastermeme.presentation.screens.home.handling

import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiState

data class HomeUiState(
    val memes: List<String> = emptyList(),
    val bottomSheetOpened: Boolean = false
) : UiState
