package com.serhiiromanchuk.mastermeme.presentation.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.mastermeme.presentation.core.utils.TopBarSortItem

@Composable
internal fun SortOptionsDropdown(
    selectedItem: TopBarSortItem,
    onSortOptionSelected: (TopBarSortItem) -> Unit,
    modifier: Modifier
) {
    val menuItems = listOf(
        TopBarSortItem.Favourite,
        TopBarSortItem.Newest
    )

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.wrapContentSize(Alignment.Center)
    ) {
        Row(
            modifier = Modifier
                .clickable { expanded = true },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = selectedItem.text,
                style = MaterialTheme.typography.bodyMedium
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
            menuItems.forEach { sortItem ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = sortItem.text,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onSortOptionSelected(sortItem)
                        expanded = false
                    }
                )
            }
        }
    }
}