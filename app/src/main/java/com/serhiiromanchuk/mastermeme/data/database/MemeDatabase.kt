package com.serhiiromanchuk.mastermeme.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.serhiiromanchuk.mastermeme.data.entity.MemeDb

@Database(entities = [MemeDb::class], version = 1, exportSchema = false)
abstract class MemeDatabase : RoomDatabase() {

    abstract fun getMemeDao(): MemeDao
}