package com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling

import android.graphics.Picture
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import com.serhiiromanchuk.mastermeme.presentation.core.base.common.UiEvent

sealed interface EditorUiEvent : UiEvent {
    data object SaveMemeClicked : EditorUiEvent
    data object BottomBarModeChanged : EditorUiEvent
    data object BottomSheetDismissed : EditorUiEvent
    data object SaveToDeviceClicked : EditorUiEvent
    data object ShareMemeClicked : EditorUiEvent
    data class ShowLeaveDialog(val isVisible: Boolean) : EditorUiEvent
    data object LeaveDialogConfirmClicked : EditorUiEvent
    data class ShowEditTextDialog(val isVisible: Boolean) : EditorUiEvent
    data class FontSizeChanged(val fontSize: Float) : EditorUiEvent
    data class ConfirmEditDialogClicked(val text: String) : EditorUiEvent
    data object ResetEditingClicked: EditorUiEvent
    data object ApplyEditingClicked: EditorUiEvent
    data class EditTextClicked(val memeId: Int) : EditorUiEvent
    data class EditTextOffsetChanged(val offset: Offset) : EditorUiEvent
    data class DeleteEditTextClicked(val memeId: Int) : EditorUiEvent
    data class EditingBoxSizeDetermined(val width: Float, val height: Float) : EditorUiEvent
    data class EditingIconHeightDetermined(val height: Float) : EditorUiEvent
    data class EditingTextHeightDetermined(val height: Float) : EditorUiEvent
    data class MemeImageSizeDetermined(val width: Float, val height: Float) : EditorUiEvent
    data class PictureSaved(val memePicture: Picture) : EditorUiEvent
    data object FontFamilyItemClicked : EditorUiEvent
    data object FontSizeItemClicked : EditorUiEvent
    data object ColorPickedItemClicked : EditorUiEvent
    data class ColorPicked(val color: Color) : EditorUiEvent
    data class FontFamilyPicked(val fontFamily: FontFamily) : EditorUiEvent
}