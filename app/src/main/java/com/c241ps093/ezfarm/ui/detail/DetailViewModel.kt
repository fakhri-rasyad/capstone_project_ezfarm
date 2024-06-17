package com.c241ps093.ezfarm.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c241ps093.ezfarm.data.database.PlantTodo
import com.c241ps093.ezfarm.data.entity.TrackingDataResponse
import com.c241ps093.ezfarm.data.repository.EzFarmRepository
import com.c241ps093.ezfarm.getErrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val ezFarmRepository: EzFarmRepository) : ViewModel() {
    fun getTodoFromDatabase(plantId: Int, todoDate: Int) : LiveData<List<PlantTodo>> = ezFarmRepository.getTodoFromDatabase(plantId, todoDate)
    fun checkTodo(plantId: Int) : LiveData<Boolean> = ezFarmRepository.checkTodo(plantId)
    fun getTodoList(plantId: Int){
        val client = ezFarmRepository.getTodoFromAPI()
        client.enqueue(object : Callback<TrackingDataResponse> {
            override fun onResponse(
                p0: Call<TrackingDataResponse>,
                p1: Response<TrackingDataResponse>
            ) {
                val responseBody = p1.body()
                if(p1.isSuccessful && responseBody !== null){
                    responseBody.data?.day?.map { dayItem->
                        dayItem?.langkah?.map {
                            val newPlantTodo = PlantTodo(
                                plantId = plantId,
                                toDoDay = dayItem.id!!,
                                desc = it?.desc!!,
                                status = it.status!!
                            )
                            ezFarmRepository.insertTodo(newPlantTodo)
                        }
                    }
                }
            }
            override fun onFailure(p0: Call<TrackingDataResponse>, p1: Throwable) {
                Log.e("DetailViewModel", p1.message ?: "Error")
            }
        })
    }
    fun updateTodoList(isCompleted: Boolean, plantTodo: PlantTodo) {
        CoroutineScope(Dispatchers.IO).launch{
            plantTodo.status = isCompleted
            ezFarmRepository.insertTodo(plantTodo)
        }
    }

}
