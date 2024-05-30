package com.c241ps093.ezfarm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c241ps093.ezfarm.data.repository.EzFarmRepository
import java.util.Calendar

class HomeViewModel(private val ezFarmRepository: EzFarmRepository) : ViewModel() {
    private var _plantList = MutableLiveData<List<DummyData>>()
    val plantList : LiveData<List<DummyData>> = _plantList

    init {
        val arrayList = arrayListOf<DummyData>()
        val plantDate = Calendar.getInstance()
        val harvestDate = Calendar.getInstance()
        harvestDate.add(Calendar.DAY_OF_MONTH, 90)
        for(i in 1..10){
            arrayList.add(
                DummyData(
                    "Padi",
                    plantDate.time,
                    harvestDate.time,
                    "Seeding"
                )
            )
        }

        _plantList.value = arrayList
    }

    fun addData(dummyData: DummyData){
        val newPlant = arrayListOf<DummyData>()
        newPlant.add(dummyData)
        _plantList.postValue(newPlant)
    }
}