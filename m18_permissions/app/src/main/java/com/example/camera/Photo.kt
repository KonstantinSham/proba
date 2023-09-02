package com.example.camera

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Photo")
data class Photo(
    @PrimaryKey
    @ColumnInfo(name = "route")
    val route: String
)
