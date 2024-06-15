package com.c241ps093.ezfarm.ui.detail

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c241ps093.ezfarm.R
import com.c241ps093.ezfarm.data.database.Plant
import com.c241ps093.ezfarm.data.database.PlantTodo
import com.c241ps093.ezfarm.databinding.ActivityDetailBinding
import com.c241ps093.ezfarm.dateFormat
import com.c241ps093.ezfarm.viewmodel.factory.ViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel(this)

        val plantData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(PLANT_DATA, Plant::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(PLANT_DATA)
        }

        if (plantData != null) {
            val plantId = plantData.id
            val todoDate = getDayDifference(plantData)
            viewModel.checkTodo(plantId).observe(this){todoExist ->
                if(!todoExist){
                    viewModel.getTodoList(plantId)
                }
                viewModel.getTodoFromDatabase(plantId, todoDate).observe(this){ listOfPlantTodo ->
                    binding.progressBar.visibility = View.INVISIBLE
                    Log.e("DETAIL", listOfPlantTodo.toString())
                    if(listOfPlantTodo.isNotEmpty()){
                        setUpRecyclerView(listOfPlantTodo)
                        binding.apply {
                            plantName.text = plantData.plantType
                            plantStatus.text = plantData.growthStatus
                            dayTitle.text = getString(R.string.day, listOfPlantTodo[0].toDoDay.toString())
                            backButton.setOnClickListener {
                                finish()
                            }
                        }
                    }
                }
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayDifference(plantData: Plant?): Int {
        val formatter = DateTimeFormatter.ofPattern(dateFormat)
        val today = LocalDate.now()
        val plantedDate = LocalDate.parse(plantData?.plantedDate, formatter)
        return (ChronoUnit.DAYS.between(plantedDate, today) + 1).toInt()
    }

    private fun setUpRecyclerView(todoList: List<PlantTodo?>?) {
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rvStorylist.apply {
            layoutManager = linearLayoutManager
            adapter = DetailRecyclerAdapter(todoList, ::updateTodo)
        }
    }

    private fun updateTodo(isCompleted: Boolean, plantTodo: PlantTodo) {
        viewModel.updateTodoList(
            isCompleted,
            plantTodo
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getViewModel(appCompatActivity: AppCompatActivity): DetailViewModel {
        val viewModelFactory = ViewModelFactory.getInstance(appCompatActivity.application)
        return ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
    }

    companion object {
        const val PLANT_DATA = "plant_data"
    }
}