package com.serhiiromanchuk.mastermeme.presentation.screens.editor.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.serhiiromanchuk.mastermeme.R

@Composable
fun ResetIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = stringResource(R.string.reset_font_size),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun ApplyIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_done),
            contentDescription = stringResource(R.string.apply_font_size),
            tint = Color.Unspecified
        )
    }
}