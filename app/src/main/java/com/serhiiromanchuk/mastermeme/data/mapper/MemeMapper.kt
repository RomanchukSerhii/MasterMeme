package com.serhiiromanchuk.mastermeme.data.mapper

import com.serhiiromanchuk.mastermeme.data.entity.MemeDb
import com.serhiiromanchuk.mastermeme.domain.entity.Meme

fun Meme.toMemeDb(): MemeDb = MemeDb(
    id = id,
    filePath = filePath,
    isFavourite = isFavourite,
    isSelected = isSelected,
    creationTimestamp = creationTimestamp
)

fun MemeDb.toMeme(): Meme = Meme(
    id = id,
    filePath = filePath,
    isFavourite = isFavourite,
    isSelected = isSelected,
    creationTimestamp = creationTimestamp
)

fun List<MemeDb>.toEntities(): List<Meme> = map { it.toMeme() }

fun List<Meme>.toEntitiesDb(): List<MemeDb> = map { it.toMemeDb() }