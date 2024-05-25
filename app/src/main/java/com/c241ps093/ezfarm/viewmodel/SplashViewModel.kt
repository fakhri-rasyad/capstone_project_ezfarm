package com.c241ps093.ezfarm.viewmodel

import androidx.lifecycle.ViewModel
import com.c241ps093.ezfarm.data.repository.EzFarmRepository
import kotlinx.coroutines.runBlocking

class SplashViewModel(private val ezFarmRepository: EzFarmRepository) : ViewModel() {
    suspend fun setHasUsedApp(){
        ezFarmRepository.setHasUsedApp()
    }

    fun checkHasUsedApp() : Boolean{
        return runBlocking {ezFarmRepository.checkIfHasUsedApp()}
    }
}