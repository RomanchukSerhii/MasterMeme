package com.serhiiromanchuk.mastermeme.presentation.screens.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.serhiiromanchuk.mastermeme.R

@Composable
fun HomeTopBar(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = modifier,
            text = stringResource(R.string.your_memes),
            style = MaterialTheme.typography.titleMedium
        )
    }
}