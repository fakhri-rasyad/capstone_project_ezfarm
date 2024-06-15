package com.c241ps093.ezfarm.data.repository

import androidx.lifecycle.LiveData
import com.c241ps093.ezfarm.BuildConfig
import com.c241ps093.ezfarm.data.database.Plant
import com.c241ps093.ezfarm.data.database.PlantDatabase
import com.c241ps093.ezfarm.data.database.PlantTodo
import com.c241ps093.ezfarm.data.datastore.UserPreferences
import com.c241ps093.ezfarm.data.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
    //Database

    fun insertPlant(plant: Plant) {
        executorService.execute { plantDatabase.ezFarmDao().insertPlant(plant) }
    }
    fun getPlant(): LiveData<List<Plant>> {
        return plantDatabase.ezFarmDao().getPlant()
    }
    fun checkTodo(plantId: Int) : LiveData<Boolean> =  plantDatabase.ezFarmDao().checkIfPlantTodoExist(plantId)
    fun getTodoFromAPI() = apiService.getTrackingData()
    fun getTodoFromDatabase(plantId : Int, todoDate: Int) = plantDatabase.ezFarmDao().getTodo(plantId, todoDate)
    fun insertTodo(todo: PlantTodo){
        executorService.execute { plantDatabase.ezFarmDao().insertTodo(todo) }
    }
    fun updateTodo(todoId : Int, plantStatus: Boolean) = plantDatabase.ezFarmDao().updateTodo(plantStatus, todoId)

    //Network
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