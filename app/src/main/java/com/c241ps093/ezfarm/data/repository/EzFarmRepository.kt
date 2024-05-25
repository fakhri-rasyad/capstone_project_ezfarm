package com.c241ps093.ezfarm.data.repository

import com.c241ps093.ezfarm.data.datastore.UserPreferences
import kotlinx.coroutines.flow.first

class EzFarmRepository(
    private val userPreferences: UserPreferences
) {
    suspend fun setHasUsedApp(){
        userPreferences.setHasUsedApp()
    }

    suspend fun checkIfHasUsedApp() : Boolean {
        return userPreferences.checkIfHasUsedApp().first()
    }

    companion object {
        @Volatile
        private var INSTANCE : EzFarmRepository? = null
        fun getInstance(
            userPreferences: UserPreferences,
        ) : EzFarmRepository {
            return INSTANCE ?: synchronized(this){
                val instance = EzFarmRepository(userPreferences)
                INSTANCE = instance
                instance

            }
        }
    }
}