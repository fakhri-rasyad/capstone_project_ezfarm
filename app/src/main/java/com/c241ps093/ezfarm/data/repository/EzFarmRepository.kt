package com.c241ps093.ezfarm.data.repository

import androidx.lifecycle.LiveData
import com.c241ps093.ezfarm.data.database.Plant
import com.c241ps093.ezfarm.data.database.PlantDatabase
import com.c241ps093.ezfarm.data.datastore.UserPreferences
import kotlinx.coroutines.flow.first
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EzFarmRepository(
    private val userPreferences: UserPreferences,
    private val plantDatabase: PlantDatabase,
) {

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    suspend fun setHasUsedApp(){
        userPreferences.setHasUsedApp()
    }

    suspend fun checkIfHasUsedApp() : Boolean {
        return userPreferences.checkIfHasUsedApp().first()
    }

    fun insertPlant(plant: Plant) {
        executorService.execute { plantDatabase.plantDao().insert(plant) }
    }

    fun getPlant() : LiveData<List<Plant>> {
        return plantDatabase.plantDao().getPlant()
    }

    companion object {
        @Volatile
        private var INSTANCE : EzFarmRepository? = null
        fun getInstance(
            userPreferences: UserPreferences,
            plantDatabase: PlantDatabase
        ) : EzFarmRepository {
            return INSTANCE ?: synchronized(this){
                val instance = EzFarmRepository(userPreferences, plantDatabase)
                INSTANCE = instance
                instance

            }
        }
    }
}