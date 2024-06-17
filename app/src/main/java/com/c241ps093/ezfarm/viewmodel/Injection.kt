package com.c241ps093.ezfarm.viewmodel

import android.content.Context
import com.c241ps093.ezfarm.data.database.PlantDatabase
import com.c241ps093.ezfarm.data.datastore.UserPreferences
import com.c241ps093.ezfarm.data.datastore.datastore
import com.c241ps093.ezfarm.data.repository.EzFarmRepository
import com.c241ps093.ezfarm.data.retrofit.ApiConfig

object Injection {
    fun injectRepository(context: Context): EzFarmRepository {
        val userPreferences = UserPreferences.getInstance(context.datastore)
        val plantDatabase = PlantDatabase.getDatabase(context)
        val apiService = ApiConfig.getInstance()
        return EzFarmRepository.getInstance(userPreferences, plantDatabase, apiService)
    }
}