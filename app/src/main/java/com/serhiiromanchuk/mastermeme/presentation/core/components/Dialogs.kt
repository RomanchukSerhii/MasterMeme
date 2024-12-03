package com.serhiiromanchuk.mastermeme.presentation.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.theme.Manrope

@Composable
fun BasicDialog(
    modifier: Modifier = Modifier,
    headline: String,
    supportingText: String = "",
    confirmButtonName: String = "",
    cancelButtonName: String = "",
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                DialogHeader(
                    headline = headline,
                    supportingText = supportingText
                )

                Spacer(modifier = Modifier.height(24.dp))

                DialogButtons(
                    cancelButtonName = cancelButtonName,
                    confirmButtonName = confirmButtonName,
                    onCancelClicked = onDismissRequest,
                    onConfirmClicked = onConfirm
                )
            }
        }
    }
}

@Composable
fun DialogWithTextField(
    modifier: Modifier = Modifier,
    headline: String,
    initialText: String = "",
    confirmButtonName: String = stringResource(R.string.ok),
    cancelButtonName: String = stringResource(R.string.cancel),
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                var textState by remember { mutableStateOf(TextFieldValue(initialText)) }

                LaunchedEffect(Unit) {
                    textState = textState.copy(selection = TextRange(0, textState.text.length))
                }

                DialogHeader(headline = headline)

                DialogTextField(
                    value = textState,
                    onValueChange = { textState = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                DialogButtons(
                    cancelButtonName = cancelButtonName,
                    confirmButtonName = confirmButtonName,
                    onCancelClicked = onDismissRequest,
                    onConfirmClicked = { onConfirm(textState.text) }
                )
            }
        }
    }
}

@Composable
private fun DialogHeader(
    modifier: Modifier = Modifier,
    headline: String,
    supportingText: String? = null
) {
    Column(
        modifier = modifier
    ) {
        // Headline
        Text(
            text = headline,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (supportingText != null) {
            Spacer(modifier = Modifier.height(16.dp))

            // SupportingText
            Text(
                text = supportingText,
                style = TextStyle(
                    fontFamily = Manrope,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Composable
private fun DialogButtons(
    modifier: Modifier = Modifier,
    cancelButtonName: String,
    confirmButtonName: String,
    onCancelClicked: () -> Unit,
    onConfirmClicked: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Right
    ) {
        // Cancel text button
        TextButton(
            onClick = onCancelClicked
        ) {
            Text(
                text = cancelButtonName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Confirm text button
        TextButton(
            onClick = onConfirmClicked
        ) {
            Text(
                text = confirmButtonName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun DialogTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            fontFamily = Manrope,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            color = Color.White
        ),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent
        )
    )
}