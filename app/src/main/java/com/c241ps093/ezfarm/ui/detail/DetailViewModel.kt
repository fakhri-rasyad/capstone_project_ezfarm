package com.c241ps093.ezfarm.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c241ps093.ezfarm.data.database.PlantTodo
import com.c241ps093.ezfarm.data.entity.TrackingDataResponse
import com.c241ps093.ezfarm.data.repository.EzFarmRepository
import com.c241ps093.ezfarm.getErrorResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val ezFarmRepository: EzFarmRepository) : ViewModel() {
    private val _toDo = MutableLiveData<List<PlantTodo>>()
    val toDo : LiveData<List<PlantTodo>> = _toDo
    fun getTodoList(plantId: Int, todoDate : Int){
        if(ezFarmRepository.checkTodo(plantId)){
            _toDo.postValue(ezFarmRepository.getTodoFromDatabase(plantId = plantId, todoDate = todoDate))
        } else {
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
                                    toDoDay = todoDate,
                                    desc = it?.desc!!,
                                    status = it.status!!
                                )
                                ezFarmRepository.insertTodo(newPlantTodo)
                            }
                        }
                        getTodoList(plantId, todoDate)
                    }
                }

                override fun onFailure(p0: Call<TrackingDataResponse>, p1: Throwable) {
                    Log.e("DetailViewModel", p1.message ?: "Error")
                }
            })
        }
    }
    fun updateTodoList(isCompleted: Boolean, todoId : Int) {
        ezFarmRepository.updateTodo(todoId, isCompleted)
    }

}
