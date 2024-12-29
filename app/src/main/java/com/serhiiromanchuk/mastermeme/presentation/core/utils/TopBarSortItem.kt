package com.serhiiromanchuk.mastermeme.presentation.core.utils

sealed class TopBarSortItem(val text: String) {
    data object Favourite : TopBarSortItem("Favourite first")
    data object Newest : TopBarSortItem("Newest first")
}

