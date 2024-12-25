package com.serhiiromanchuk.mastermeme.presentation.screens.home.handling

import com.serhiiromanchuk.mastermeme.presentation.core.base.common.ActionEvent

sealed interface HomeActionEvent : ActionEvent {
    data class NavigateToEditor(val memeResId: Int) : HomeActionEvent
}