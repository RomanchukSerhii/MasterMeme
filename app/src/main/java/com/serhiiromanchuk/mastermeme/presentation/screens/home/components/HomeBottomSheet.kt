package com.serhiiromanchuk.mastermeme.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiState.BottomSheetState
import com.serhiiromanchuk.mastermeme.presentation.theme.Manrope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomSheet(
    uiSheetState: BottomSheetState,
    onEvent: (HomeUiEvent) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    if (uiSheetState.isOpened) {
        ModalBottomSheet(
            onDismissRequest = { onEvent(HomeUiEvent.BottomSheetDismissed) },
            sheetState = bottomSheetState
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
//                Text(
//                    text = stringResource(R.string.choose_template),
//                    style = MaterialTheme.typography.bodyLarge
//                )
//                Spacer(Modifier.height(18.dp))
//                Text(
//                    text = stringResource(R.string.choose_template_description),
//                    style = MaterialTheme.typography.bodySmall
//                )
                BottomSheetHeader(
                    uiSheetState = uiSheetState,
                    onEvent = onEvent
                )
                Spacer(Modifier.height(36.dp))
                MemeBottomSheetVerticalGrid(
                    modifier = Modifier.padding(6.dp),
                    memes = uiSheetState.memeTemplates,
                    onMemeClicked = { memeResId -> onEvent(HomeUiEvent.MemeClicked(memeResId)) }
                )
            }
        }
    }
}

@Composable
private fun BottomSheetHeader(
    modifier: Modifier = Modifier,
    uiSheetState: BottomSheetState,
    onEvent: (HomeUiEvent) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(uiSheetState.isSearchMode) {
        if (uiSheetState.isSearchMode) {
            focusRequester.requestFocus()
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        if (uiSheetState.isSearchMode) {
            SearchField(
                searchText = uiSheetState.searchText,
                templatesCounter = uiSheetState.memeTemplates.size,
                focusRequester = focusRequester,
                onEvent = onEvent
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.choose_template),
                    style = MaterialTheme.typography.bodyLarge
                )

                // Search icon
                IconButton(
                    onClick = { onEvent(HomeUiEvent.SearchModeToggled) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_meme_template)
                    )
                }
            }

            Spacer(Modifier.height(18.dp))
            Text(
                text = stringResource(R.string.choose_template_description),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun SearchField(
    modifier: Modifier = Modifier,
    searchText: String,
    templatesCounter: Int,
    focusRequester: FocusRequester,
    onEvent: (HomeUiEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        // Search field
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back arrow
            IconButton(
                onClick = { onEvent(HomeUiEvent.SearchModeToggled) }
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = stringResource(R.string.back_from_search_template),
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Search text field
            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .focusRequester(focusRequester),
                value = searchText,
                onValueChange = { onEvent(HomeUiEvent.SearchedTextChanged(it)) },
                textStyle = TextStyle(
                    fontFamily = Manrope,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                ),
                cursorBrush = SolidColor(Color.White),
                maxLines = 1,
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (searchText.isEmpty()) {
                            Text(
                                text = stringResource(R.string.search_input),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFF4A494D),
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        } else innerTextField()
                    }
                }
            )

            // Clear text button
            if (searchText.isNotEmpty()) {
                IconButton(
                    onClick = { onEvent(HomeUiEvent.SearchedTextChanged("")) }
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = stringResource(R.string.clear_text_button),
                        tint = Color.Unspecified
                    )
                }
            }
        }

        HorizontalDivider(thickness = 1.dp, color = Color(0xFF3C3841))

        Spacer(modifier = Modifier.height(16.dp))

        // Templates counter
        Text(
            text = when (templatesCounter) {
                0 -> stringResource(R.string.no_memes_found)
                1 -> stringResource(R.string.one_template)
                else -> stringResource(R.string.templates, templatesCounter)
            },
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.outline
            )
        )
    }
}