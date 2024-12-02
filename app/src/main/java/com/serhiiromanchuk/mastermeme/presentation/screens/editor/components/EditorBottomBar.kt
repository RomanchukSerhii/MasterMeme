package com.serhiiromanchuk.mastermeme.presentation.screens.editor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.core.components.OutlinedButton
import com.serhiiromanchuk.mastermeme.presentation.core.components.PrimaryButton
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent

@Composable
fun EditorBottomBar(
    modifier: Modifier = Modifier,
    editMode: Boolean = false,
    onEvent: (EditorUiEvent) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(vertical = 2.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        if (!editMode) {
            Row {

                // AddTextButton
                OutlinedButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = { onEvent(EditorUiEvent.AddTextClicked) },
                    labelText = stringResource(R.string.add_text)
                )

                // SaveMemeButton
                PrimaryButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = { onEvent(EditorUiEvent.SaveMemeClicked) },
                    labelText = stringResource(R.string.save_meme)
                )
            }
        }
    }
}