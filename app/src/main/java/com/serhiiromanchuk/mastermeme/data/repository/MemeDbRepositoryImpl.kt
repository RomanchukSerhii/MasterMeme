package com.serhiiromanchuk.mastermeme.data.repository

import android.content.ContentResolver
import android.graphics.Picture
import android.net.Uri
import android.provider.MediaStore
import com.serhiiromanchuk.mastermeme.data.database.MemeDao
import com.serhiiromanchuk.mastermeme.data.entity.MemeDb
import com.serhiiromanchuk.mastermeme.data.mapper.toEntities
import com.serhiiromanchuk.mastermeme.data.mapper.toMemeDb
import com.serhiiromanchuk.mastermeme.domain.entity.Meme
import com.serhiiromanchuk.mastermeme.domain.rejpository.MemeDbRepository
import com.serhiiromanchuk.mastermeme.utils.BitmapProcessor
import com.serhiiromanchuk.mastermeme.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.File

class MemeDbRepositoryImpl(
    private val memeDao: MemeDao,
    private val bitmapProcessor: BitmapProcessor,
    private val contentResolver: ContentResolver
) : MemeDbRepository {
    override fun getAllMemes(): Flow<List<Meme>>  = memeDao.getAllMemes().map { it.toEntities() }

    override suspend fun cleanUpInvalidMemes() {
        CoroutineScope(Dispatchers.IO).launch {
            val memes = memeDao.getAllMemesSync()
            memes.forEach { memeDb ->
                val uri = Uri.parse(memeDb.filePath)
                val realPath = getRealPathFromUri(uri)
                val file = realPath?.let { File(it) }
                if (file == null || !file.exists() || !file.isFile) {
                    memeDao.deleteMeme(memeDb.id)
                }
            }
        }
    }

    override suspend fun saveMeme(memePicture: Picture): Uri {
        val bitmap = bitmapProcessor.createBitmapFromPicture(memePicture)
        val uri = bitmapProcessor.saveBitmapToDisk(bitmap)
        val filePath = uri.toString()
        val newMemeDb = MemeDb(id = Constants.INITIAL_MEME_ID, filePath = filePath)
        memeDao.upsertMeme(newMemeDb)
        return uri
    }

    override suspend fun upsertMeme(meme: Meme) = memeDao.upsertMeme(meme.toMemeDb())

    override suspend fun deleteMeme(memeId: Int) = memeDao.deleteMeme(memeId)

    private fun getRealPathFromUri(uri: Uri): String? {
        val cursor = contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndex(MediaStore.Images.Media.DATA)
            if (columnIndex != -1 && it.moveToFirst()) {
                return it.getString(columnIndex)
            }
        }
        return null
    }
}