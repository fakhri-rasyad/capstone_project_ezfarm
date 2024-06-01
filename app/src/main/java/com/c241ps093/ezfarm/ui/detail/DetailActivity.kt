package com.c241ps093.ezfarm.ui.detail

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.c241ps093.ezfarm.R
import com.c241ps093.ezfarm.data.database.Plant
import com.c241ps093.ezfarm.databinding.ActivityDetailBinding
import com.c241ps093.ezfarm.dateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class DetailActivity : AppCompatActivity() {
    private var _binding : ActivityDetailBinding? = null
    private val binding get() = _binding!!
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plantData = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(PLANT_DATA, Plant::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(PLANT_DATA)
        }

        binding.apply {
            dayTitle.text = getString(R.string.day, getDayDifference(plantData))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayDifference(plantData: Plant?): String{
        val formatter = DateTimeFormatter.ofPattern(dateFormat)
        val today = LocalDate.now()
        val plantedDate = LocalDate.parse(plantData?.plantedDate, formatter)
        val dayDifference = ChronoUnit.DAYS.between(plantedDate, today) + 1
        return dayDifference.toString()
    }

    companion object {
        const val PLANT_DATA = "plant_data"
    }
}