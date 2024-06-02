package com.c241ps093.ezfarm.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c241ps093.ezfarm.data.repository.EzFarmRepository

class DetailViewModel(private val ezFarmRepository: EzFarmRepository) : ViewModel(){
    private val _todoList = MutableLiveData<List<ToDo>>()
    val todoList : LiveData<List<ToDo>> = _todoList

    fun updateTodoList(isCompleted: Boolean, position: Int) {
        val data = todoList.value
        data?.get(position)?.todoCompleted = isCompleted
        Log.d("ViewModel", data.toString())
        _todoList.postValue(data!!)
    }

    fun getTodoData(){
        val todoData = arrayListOf<ToDo>()
        for(i in 1..10){
            val toDo = ToDo(
                todoTitle = "Water The Plan",
                todoDesc = "This is TODO DESC",
                todoCompleted = false,
                todoCategory = "Watering"
            )
            todoData.add(toDo)
        }
        _todoList.postValue(todoData)
    }
}

data class ToDo(
    val todoTitle : String,
    val todoDesc : String,
    var todoCompleted : Boolean = false,
    val todoCategory : String,
)
