package com.serhiiromanchuk.mastermeme.presentation.core.utils

sealed class DropdownSortItem(val text: String) {
    data object Favourite : DropdownSortItem("Favourite first")
    data object Newest : DropdownSortItem("Newest first")
}

