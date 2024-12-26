package com.serhiiromanchuk.mastermeme.domain.rejpository

import android.graphics.Picture
import com.serhiiromanchuk.mastermeme.domain.entity.Meme
import kotlinx.coroutines.flow.Flow

interface MemeDbRepository {

    fun getAllMemes(): Flow<List<Meme>>

    suspend fun saveMeme(memePicture: Picture)

    suspend fun upsertMeme(meme: Meme)

    suspend fun deleteMeme(memeId: Int)
}