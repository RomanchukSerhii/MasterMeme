package com.serhiiromanchuk.mastermeme.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.serhiiromanchuk.mastermeme.data.entity.MemeDb
import kotlinx.coroutines.flow.Flow

@Dao
interface MemeDao {

    @Query("""
        SELECT * FROM memes
        ORDER BY 
            isFavourite DESC, 
            CASE WHEN isFavourite = 1 THEN creationTimestamp END DESC
    """)
    fun getMemesFavouriteSorted(): Flow<List<MemeDb>>

    @Query("SELECT * FROM memes ORDER BY creationTimestamp DESC")
    fun getMemesDateSorted(): Flow<List<MemeDb>>

    @Query("SELECT * FROM memes")
    fun getAllMemesSync(): List<MemeDb>

    @Upsert
    suspend fun upsertMeme(memeDb: MemeDb)

    @Query("DELETE FROM memes WHERE id=:memeId")
    suspend fun deleteMeme(memeId: Int)
}