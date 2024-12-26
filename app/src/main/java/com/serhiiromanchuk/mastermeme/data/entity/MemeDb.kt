package com.serhiiromanchuk.mastermeme.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memes")
data class MemeDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val filePath: String
)
