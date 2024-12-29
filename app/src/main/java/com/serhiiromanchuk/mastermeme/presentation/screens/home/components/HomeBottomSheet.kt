package com.serhiiromanchuk.mastermeme.presentation.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.core.utils.MemeTemplateProvider
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomSheet(
    openBottomSheet: Boolean,
    onEvent: (HomeUiEvent) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onEvent(HomeUiEvent.BottomSheetDismissed) },
            sheetState = bottomSheetState
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.choose_template),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(18.dp))
                Text(
                    text = stringResource(R.string.choose_template_description),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.height(36.dp))
                MemeBottomSheetVerticalGrid(
                    modifier = Modifier.padding(6.dp),
                    memes = MemeTemplateProvider.memesList,
                    onMemeClicked = { memeResId -> onEvent(HomeUiEvent.MemeClicked(memeResId)) }
                )
            }
        }
    }
}