package com.c241ps093.ezfarm.viewmodel

import com.c241ps093.ezfarm.data.repository.EzFarmRepository

class SplashViewModel(private val ezFarmRepository: EzFarmRepository) {
    suspend fun setHasUsedApp(){
        ezFarmRepository.setHasUsedApp()
    }

    suspend fun checkHasUsedApp() : Boolean{
        return ezFarmRepository.checkIfHasUsedApp()
    }
}