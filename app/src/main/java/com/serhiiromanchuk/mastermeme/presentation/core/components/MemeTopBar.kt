package com.serhiiromanchuk.mastermeme.presentation.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.core.utils.TopBarSortItem
import com.serhiiromanchuk.mastermeme.presentation.screens.home.components.SortOptionsDropdown

@Composable
fun MemeTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: (() -> Unit)? = null,
    selectedItem: TopBarSortItem? = null,
    onSortOptionSelected: ((TopBarSortItem) -> Unit)? = null
) {
    Surface(
        modifier = modifier.height(64.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
            val titleAlignment = if (onBackClick != null) Alignment.Center else Alignment.CenterStart

            Text(
                modifier = Modifier.align(titleAlignment),
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            onBackClick?.let {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = onBackClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = stringResource(R.string.back_to_main_screen)
                    )
                }
            }

            if (onSortOptionSelected != null && selectedItem != null) {
                SortOptionsDropdown(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    selectedItem = selectedItem,
                    onSortOptionSelected = onSortOptionSelected
                )
            }
        }
    }
}