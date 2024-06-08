package com.c241ps093.ezfarm.data.repository

import androidx.lifecycle.LiveData
import com.c241ps093.ezfarm.data.database.Plant
import com.c241ps093.ezfarm.data.database.PlantDatabase
import com.c241ps093.ezfarm.data.datastore.UserPreferences
import com.c241ps093.ezfarm.data.retrofit.ApiService
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EzFarmRepository(
    private val userPreferences: UserPreferences,
    private val plantDatabase: PlantDatabase,
    private val apiService: ApiService,
) {

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    suspend fun setHasUsedApp() {
        userPreferences.setHasUsedApp()
    }

    suspend fun checkIfHasUsedApp(): Boolean {
        return userPreferences.checkIfHasUsedApp().first()
    }

    fun insertPlant(plant: Plant) {
        executorService.execute { plantDatabase.plantDao().insert(plant) }
    }

    fun getPlant(): LiveData<List<Plant>> {
        return plantDatabase.plantDao().getPlant()
    }

    fun uploadImage(file : MultipartBody.Part) = apiService.postData(file = file)

    companion object {
        @Volatile
        private var INSTANCE: EzFarmRepository? = null
        fun getInstance(
            userPreferences: UserPreferences,
            plantDatabase: PlantDatabase,
            apiService: ApiService
        ): EzFarmRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = EzFarmRepository(userPreferences, plantDatabase, apiService)
                INSTANCE = instance
                instance

            }
        }
    }
}