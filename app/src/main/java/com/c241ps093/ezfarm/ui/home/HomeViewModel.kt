package com.c241ps093.ezfarm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.c241ps093.ezfarm.data.database.Plant
import com.c241ps093.ezfarm.data.repository.EzFarmRepository

class HomeViewModel(private val ezFarmRepository: EzFarmRepository) : ViewModel() {
    fun getPlant() : LiveData<List<Plant>> = ezFarmRepository.getPlant()


    fun addData(plant: Plant){
        ezFarmRepository.insertPlant(plant)
    }
}