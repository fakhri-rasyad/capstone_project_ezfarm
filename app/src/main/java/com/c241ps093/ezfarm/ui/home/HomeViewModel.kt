package com.c241ps093.ezfarm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c241ps093.ezfarm.data.database.Plant
import com.c241ps093.ezfarm.data.repository.EzFarmRepository
import java.util.Calendar

class HomeViewModel(private val ezFarmRepository: EzFarmRepository) : ViewModel() {
    private var _plantList = MutableLiveData<List<DummyData>>()
    val plantList : LiveData<List<DummyData>> = _plantList
    fun getPlant() : LiveData<List<Plant>> = ezFarmRepository.getPlant()


    fun addData(plant: Plant){
        ezFarmRepository.insertPlant(plant)
    }
}