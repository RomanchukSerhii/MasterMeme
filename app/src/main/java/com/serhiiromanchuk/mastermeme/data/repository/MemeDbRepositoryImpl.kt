package com.serhiiromanchuk.mastermeme.data.repository

import android.graphics.Picture
import com.serhiiromanchuk.mastermeme.data.database.MemeDao
import com.serhiiromanchuk.mastermeme.data.entity.MemeDb
import com.serhiiromanchuk.mastermeme.data.mapper.toEntities
import com.serhiiromanchuk.mastermeme.data.mapper.toMemeDb
import com.serhiiromanchuk.mastermeme.domain.entity.Meme
import com.serhiiromanchuk.mastermeme.domain.rejpository.MemeDbRepository
import com.serhiiromanchuk.mastermeme.utils.BitmapProcessor
import com.serhiiromanchuk.mastermeme.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MemeDbRepositoryImpl(
    private val memeDao: MemeDao,
    private val bitmapProcessor: BitmapProcessor
) : MemeDbRepository {
    override fun getAllMemes(): Flow<List<Meme>>  = memeDao.getAllMemes().map { it.toEntities() }

    override suspend fun saveMeme(memePicture: Picture) {
        val bitmap = bitmapProcessor.createBitmapFromPicture(memePicture)
        val uri = bitmapProcessor.saveBitmapToDisk(bitmap)
        val filePath = uri.toString()
        val newMemeDb = MemeDb(id = Constants.INITIAL_MEME_ID, filePath = filePath)
        memeDao.upsertMeme(newMemeDb)
    }

    override suspend fun upsertMeme(meme: Meme) = memeDao.upsertMeme(meme.toMemeDb())

    override suspend fun deleteMeme(memeId: Int) = memeDao.deleteMeme(memeId)
}