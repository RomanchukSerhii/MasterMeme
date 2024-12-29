package com.serhiiromanchuk.mastermeme.presentation.screens.home.components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.domain.entity.Meme
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent

@Composable
fun MemeItem(
    modifier: Modifier = Modifier,
    meme: Meme,
    isSelectionMode: Boolean,
    onEvent: (HomeUiEvent) -> Unit
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val memeUri = Uri.parse(meme.filePath)

    LaunchedEffect(isSelectionMode) {
        Log.d("SelectionMode", "isSelectionMode: $isSelectionMode")
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .pointerInput(isSelectionMode, meme.isSelected) {
                detectTapGestures(
                    onLongPress = {
                        if (!isSelectionMode) {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onEvent(HomeUiEvent.MemeLongPressed(meme))
                        }
                    },
                    onTap = {
                        if (isSelectionMode) {
                            onEvent(HomeUiEvent.MemeSelectionToggled(meme))
                        }
                    }
                )
            },
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(memeUri)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ic_delete_edit)
        )

        // SelectIcon
        if (isSelectionMode) {
            IconButton(
                onClick = { onEvent(HomeUiEvent.MemeSelectionToggled(meme)) },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    painter = if (meme.isSelected) {
                        painterResource(R.drawable.ic_selected)
                    } else painterResource(R.drawable.ic_unselected),
                    contentDescription = stringResource(R.string.selected_checkbox),
                    tint = Color.Unspecified
                )
            }
        }

        // FavouriteIcon
        if (!isSelectionMode) {
            IconButton(
                onClick = { onEvent(HomeUiEvent.MemeFavouriteToggled(meme.id)) },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = if (meme.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.selected_checkbox),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}