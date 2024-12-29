package com.serhiiromanchuk.mastermeme.domain.entity

import androidx.compose.runtime.Stable
import com.serhiiromanchuk.mastermeme.utils.Constants
import java.time.Instant

@Stable
data class Meme(
    val id: Int = Constants.INITIAL_MEME_ID,
    val filePath: String = "",
    val isFavourite: Boolean = false,
    val isSelected: Boolean = false,
    val creationTimestamp: Instant = Instant.now()
)
