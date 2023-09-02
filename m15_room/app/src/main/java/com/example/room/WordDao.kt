package com.example.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM dictionary LIMIT 5")
    fun getAll(): Flow<List<Word>>

    @Insert
    suspend fun insert(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Update
    suspend fun update(word: Word)

}