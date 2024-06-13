package com.c241ps093.ezfarm.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EzFarmDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPlant(plant: Plant)

    @Delete
    fun deletePlant(plant: Plant)

    @Query("SELECT * from plant ORDER BY id ASC")
    fun getPlant(): LiveData<List<Plant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todo: PlantTodo)

    @Query("SELECT * FROM planttodo WHERE plantId LIKE :plantId AND todoDay LIKE :todoDate")
    fun getTodo(plantId : Int, todoDate: Int) : List<PlantTodo>

    @Query("UPDATE planttodo SET status=:newStatus WHERE todoId = :todoId")
    fun updateTodo(newStatus: Boolean, todoId: Int)

    @Query("SELECT EXISTS(SELECT * FROM planttodo WHERE plantId LIKE :plantId)")
    fun checkIfPlantTodoExist(plantId : Int) : Boolean
}