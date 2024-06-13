package com.c241ps093.ezfarm.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c241ps093.ezfarm.data.entity.Data
import com.c241ps093.ezfarm.data.entity.DayItem
import com.c241ps093.ezfarm.data.entity.LangkahItem
import com.c241ps093.ezfarm.data.entity.TrackingDataResponse
import com.c241ps093.ezfarm.data.repository.EzFarmRepository

class DetailViewModel(private val ezFarmRepository: EzFarmRepository) : ViewModel() {

    fun updateTodoList(isCompleted: Boolean, position: Int) {
        val data = trackingData.value
        _trackingData.postValue(data!!)
    }

}
