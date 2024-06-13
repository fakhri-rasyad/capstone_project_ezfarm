package com.c241ps093.ezfarm.ui.detail

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c241ps093.ezfarm.data.database.Plant
import com.c241ps093.ezfarm.data.entity.LangkahItem
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
        viewModel.trackingData.observe(this) {
            val data = it[0]
            setUpRecyclerView(data?.langkah)
            binding.apply {
                plantName.text = plantData?.plantType
                plantStatus.text = plantData?.growthStatus
                dayTitle.text = data?.day
                backButton.setOnClickListener {
                    finish()
                }
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayDifference(plantData: Plant?): String {
        val formatter = DateTimeFormatter.ofPattern(dateFormat)
        val today = LocalDate.now()
        val plantedDate = LocalDate.parse(plantData?.plantedDate, formatter)
        val dayDifference = ChronoUnit.DAYS.between(plantedDate, today) + 1
        return dayDifference.toString()
    }

    private fun setUpRecyclerView(todoList: List<LangkahItem?>?) {
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rvStorylist.apply {
            layoutManager = linearLayoutManager
            adapter = DetailRecyclerAdapter(todoList, ::updateTodo)
        }
    }

    private fun updateTodo(isCompleted: Boolean, position: Int) {
        viewModel.updateTodoList(
            isCompleted,
            position
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