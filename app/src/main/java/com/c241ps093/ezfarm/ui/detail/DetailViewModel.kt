package com.c241ps093.ezfarm.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c241ps093.ezfarm.data.entity.LangkahLangkahItem
import com.c241ps093.ezfarm.data.entity.TrackingDataResponse
import com.c241ps093.ezfarm.data.entity.TrackingDataResponseItem
import com.c241ps093.ezfarm.data.repository.EzFarmRepository

class DetailViewModel(private val ezFarmRepository: EzFarmRepository) : ViewModel() {
    private val _todoList = MutableLiveData<List<LangkahLangkahItem>>()
    val todoList: LiveData<List<LangkahLangkahItem>> = _todoList

    private val paddyTrackingData = TrackingDataResponse(
        trackingDataResponse = listOf(
            TrackingDataResponseItem(
                hari = 1,
                urlGambar = "https://picsum.photos/200/300",
                langkahLangkah = listOf(
                    LangkahLangkahItem(
                        deskripsi = "Prepare the field by plowing and leveling the soil.",
                        tipe = "Preparation",
                        status = false
                    ),
                    LangkahLangkahItem(
                        deskripsi = "Sow the seeds in the nursery.",
                        tipe = "Sowing",
                        status = true
                    )
                ),
                judul = "Day 1: Field Preparation and Sowing"
            )
        )
    )

    private val _trackingData = MutableLiveData<List<TrackingDataResponseItem?>>()
    val trackingData: LiveData<List<TrackingDataResponseItem?>> = _trackingData

    init {
        getTodoData()
    }

    fun updateTodoList(isCompleted: Boolean, position: Int) {
        val data = trackingData.value
        data?.get(0)?.langkahLangkah?.get(position)?.status = isCompleted
        _trackingData.postValue(data!!)
    }

    fun getTodoData() {
        _trackingData.postValue(paddyTrackingData.trackingDataResponse!!)
    }
}
