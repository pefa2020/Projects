package com.example.bitfit

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EdibleDao {
    @Query("SELECT * FROM edible_table")
    fun getAll(): Flow<List<EdibleEntity>>

    @Insert
    fun insertAll(edibles: List<EdibleEntity>)

    @Insert
    fun insert(edible: EdibleEntity)

    @Delete
    fun delete(edible: EdibleEntity)

    @Query("DELETE FROM edible_table WHERE id = :id")
    fun deleteById(id: Long)

    @Query("DELETE FROM edible_table")
    fun deleteAll()
}