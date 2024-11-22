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

    @Query("SELECT MIN(ET.calories) FROM edible_table ET")
    fun getMinCalories(): Int

    @Query("SELECT MAX(ET.calories) FROM edible_table ET")
    fun getMaxCalories(): Int

    @Query("SELECT AVG(ET.calories) FROM edible_table ET")
    fun getAvgCalories(): Int

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