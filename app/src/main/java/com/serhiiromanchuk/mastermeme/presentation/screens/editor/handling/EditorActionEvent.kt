package com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling

import android.net.Uri
import com.serhiiromanchuk.mastermeme.presentation.core.base.common.ActionEvent

sealed interface EditorActionEvent : ActionEvent {
    data object NavigationBack : EditorActionEvent
    data object ShowToast : EditorActionEvent
    data class ShareMeme(val memeUri: Uri) : EditorActionEvent
}