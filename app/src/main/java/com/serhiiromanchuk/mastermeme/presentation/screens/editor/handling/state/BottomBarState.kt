package com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.state

import androidx.compose.runtime.Stable

@Stable
data class BottomBarState(
    val bottomBarEditMode: Boolean = true,
    val bottomBarItem: BottomBarItem = BottomBarItem.Initial
) {
    sealed interface BottomBarItem {
        data object Initial : BottomBarItem
        data object FontFamily : BottomBarItem
        data object FontSize : BottomBarItem
        data object ColorPicker : BottomBarItem
    }
}
