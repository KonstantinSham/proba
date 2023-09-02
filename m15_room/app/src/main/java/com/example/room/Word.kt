package com.example.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary")
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "word")
    var word: String,
    @ColumnInfo(name = "counts")
    var counts: Int
)