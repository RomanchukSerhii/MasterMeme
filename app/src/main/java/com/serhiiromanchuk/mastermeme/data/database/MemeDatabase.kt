package com.serhiiromanchuk.mastermeme.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.serhiiromanchuk.mastermeme.data.converter.InstantConverter
import com.serhiiromanchuk.mastermeme.data.entity.MemeDb

@Database(entities = [MemeDb::class], version = 1, exportSchema = false)
@TypeConverters(InstantConverter::class)
abstract class MemeDatabase : RoomDatabase() {
    abstract fun getMemeDao(): MemeDao
}