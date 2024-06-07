package com.c241ps093.ezfarm.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val firstUsage = booleanPreferencesKey(name = "first_time_use")

    suspend fun setHasUsedApp() {
        dataStore.edit {
            it[this.firstUsage] = true
        }
    }

    fun checkIfHasUsedApp(): Flow<Boolean> {
        return dataStore.data.map {
            it[firstUsage] ?: false
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}