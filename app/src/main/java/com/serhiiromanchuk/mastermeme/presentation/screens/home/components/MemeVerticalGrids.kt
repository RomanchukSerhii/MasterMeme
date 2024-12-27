package com.serhiiromanchuk.mastermeme.presentation.screens.home.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.domain.entity.Meme
import com.serhiiromanchuk.mastermeme.presentation.core.components.BoxFade

@Composable
fun MemeBottomSheetVerticalGrid(
    modifier: Modifier = Modifier,
    memes: List<Int>,
    onMemeClicked: (memeResId: Int) -> Unit
) {
    Box() {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(memes, key = { it }) { memeResId ->
                val interactionSource = remember {
                    MutableInteractionSource()
                }
                Image(
                    painter = painterResource(id = memeResId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(
                            interactionSource,
                            LocalIndication.current
                        ) {
                            onMemeClicked(memeResId)
                        },

                    )
            }
        }

        BoxFade(
            modifier = Modifier.height(82.dp),
            fadeColor = MaterialTheme.colorScheme.surface
        )
    }

}

@Composable
fun MemeVerticalGrid(
    modifier: Modifier = Modifier,
    memeList: List<Meme>
) {
    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(memeList, key = { it.id }) { meme ->
                val memeUri = Uri.parse(meme.filePath)

                AsyncImage(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    model = ImageRequest.Builder(context)
                        .data(memeUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = painterResource(R.drawable.ic_delete_edit)
                )
            }
        }

        BoxFade(
            modifier = Modifier.height(82.dp),
            fadeColor = MaterialTheme.colorScheme.background
        )
    }
}
