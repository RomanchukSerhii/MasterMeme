package com.serhiiromanchuk.mastermeme.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent

@Composable
fun SelectedModeTopBar(
    modifier: Modifier = Modifier,
    selectedItemCounter: Int,
    onEvent: (HomeUiEvent) -> Unit
) {
    Surface(
        modifier = modifier.height(64.dp)
    ) {
        Row(
            modifier = modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ClearIcon
            IconButton(
                onClick = { onEvent(HomeUiEvent.SelectionModeToggled) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = stringResource(R.string.clear_icon_description),
                    tint = Color.Unspecified
                )
            }

            // Counter
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                text = selectedItemCounter.toString(),
                style = MaterialTheme.typography.titleMedium
            )

            // ShareIcon
            IconButton(
                onClick = { onEvent(HomeUiEvent.ShareIconClicked) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_share),
                    contentDescription = stringResource(R.string.share_icon_description),
                    tint = Color.Unspecified
                )
            }

            // DeleteIcon
            IconButton(
                onClick = { onEvent(HomeUiEvent.ShowDeleteDialog(true)) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = stringResource(R.string.share_icon_description),
                    tint = Color.Unspecified
                )
            }
        }
    }
}