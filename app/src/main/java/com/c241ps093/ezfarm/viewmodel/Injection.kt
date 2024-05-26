package com.c241ps093.ezfarm.viewmodel

import android.content.Context
import com.c241ps093.ezfarm.data.datastore.UserPreferences
import com.c241ps093.ezfarm.data.datastore.datastore
import com.c241ps093.ezfarm.data.repository.EzFarmRepository

object Injection {
    fun injectRepository(context: Context) : EzFarmRepository {
        val userPreferences = UserPreferences.getInstance(context.datastore)
        return EzFarmRepository.getInstance(userPreferences)
    }
}