@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.serhiiromanchuk.mastermeme.presentation.screens.editor.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.screens.editor.handling.EditorUiEvent

@Composable
fun EditorBottomSheet(
    openBottomSheet: Boolean,
    onEvent: (EditorUiEvent) -> Unit
) {

    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onEvent(EditorUiEvent.BottomSheetDismissed) }
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            ) {
                // Save to device
                SaveOptions(
                    icon = painterResource(R.drawable.ic_download),
                    title = stringResource(R.string.save_to_device),
                    subtitle = stringResource(R.string.save_meme_subtitle),
                    onClick = { onEvent(EditorUiEvent.SaveToDeviceClicked) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Share the meme
                SaveOptions(
                    icon = painterResource(R.drawable.ic_share),
                    title = stringResource(R.string.share_the_meme),
                    subtitle = stringResource(R.string.share_meme_subtitle),
                    onClick = {  }
                )
            }
        }
    }
}

@Composable
private fun SaveOptions(
    icon: Painter,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Row(
        modifier = modifier
            .clickable(
                interactionSource,
                LocalIndication.current
            ) {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Subtitle
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.outline)
            )
        }
    }
}