package com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.state

import androidx.compose.runtime.Stable

@Stable
data class BottomBarState(
    val bottomBarEditMode: Boolean = true,
    val fontFamilyIconSelected: Boolean = false,
    val fontSizeIconSelected: Boolean = false,
    val colorPickerIconSelected: Boolean = false,
)
