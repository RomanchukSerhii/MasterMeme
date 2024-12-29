package com.serhiiromanchuk.mastermeme.presentation.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.serhiiromanchuk.mastermeme.R

@Composable
internal fun SortOptionsDropdown(
    selectedItemIndex: Int,
    onSortOptionSelected: (index: Int) -> Unit
) {
    val menuItems = listOf(
        stringResource(R.string.sort_favourites_first),
        stringResource(R.string.sort_newest_first),
    )

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.wrapContentSize(Alignment.Center)
    ) {
        Row(
            modifier = Modifier
                .clickable { expanded = true }
        ) {
            Text(
                text = menuItems[selectedItemIndex],
                color = MaterialTheme.colorScheme.primary
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            menuItems.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = text,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        onSortOptionSelected(index)
                        expanded = false
                    }
                )
            }
        }
    }
}