package com.c241ps093.ezfarm.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(plant: Plant)

    @Delete
    fun delete(plant: Plant)

    @Query("SELECT * from plant ORDER BY id ASC")
    fun getPlant() : LiveData<List<Plant>>
}