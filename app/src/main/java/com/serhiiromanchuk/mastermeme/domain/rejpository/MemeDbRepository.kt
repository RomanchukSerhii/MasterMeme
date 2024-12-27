package com.serhiiromanchuk.mastermeme.domain.rejpository

import android.graphics.Picture
import android.net.Uri
import com.serhiiromanchuk.mastermeme.domain.entity.Meme
import kotlinx.coroutines.flow.Flow

interface MemeDbRepository {

    fun getAllMemes(): Flow<List<Meme>>

    suspend fun cleanUpInvalidMemes()

    suspend fun saveMemeToDisk(memePicture: Picture, saveToDatabase: Boolean): Uri

    suspend fun upsertMeme(meme: Meme)

    suspend fun deleteMeme(memeId: Int)
}