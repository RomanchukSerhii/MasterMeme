package com.serhiiromanchuk.mastermeme.presentation.screens.editor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.core.components.OutlinedText

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditingText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit
) {
    EditingBox {
        OutlinedText(
            modifier = modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            text = text,
            fontSize = fontSize
        )
    }
}

@Composable
private fun EditingBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .padding(top = 10.dp, end = 10.dp)
                .border(1.dp, Color.White, RoundedCornerShape(4.dp))
                .background(Color.Transparent, RoundedCornerShape(4.dp))
        ) {
            content()
        }
        Icon(
            modifier = Modifier.align(Alignment.TopEnd),
            painter = painterResource(R.drawable.ic_delete_edit),
            contentDescription = stringResource(R.string.delete_text),
            tint = Color.Unspecified
        )
    }
}