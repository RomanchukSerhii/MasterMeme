package com.serhiiromanchuk.mastermeme.domain.entity

import com.serhiiromanchuk.mastermeme.utils.Constants

data class Meme(
    val id: Int = Constants.INITIAL_MEME_ID,
    val filePath: String = ""
)
