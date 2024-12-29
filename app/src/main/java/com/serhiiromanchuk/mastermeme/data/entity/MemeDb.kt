package com.serhiiromanchuk.mastermeme.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "memes")
data class MemeDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val filePath: String,
    val isFavourite: Boolean = false,
    val isSelected: Boolean = false,
    val creationTimestamp: Instant = Instant.now()
)
