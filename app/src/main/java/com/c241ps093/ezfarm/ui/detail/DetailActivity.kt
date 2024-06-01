package com.c241ps093.ezfarm.ui.detail

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.c241ps093.ezfarm.R
import com.c241ps093.ezfarm.data.database.Plant
import com.c241ps093.ezfarm.databinding.ActivityDetailBinding
import com.c241ps093.ezfarm.stringDateFormatter
import java.util.Calendar

class DetailActivity : AppCompatActivity() {
    private var _binding : ActivityDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plantData = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(PLANT_DATA, Plant::class.java)
        } else {
            intent.getParcelableExtra<Plant>(PLANT_DATA)
        }

        binding.apply {
            dayTitle.text = getString(R.string.day)
        }
    }

    companion object {
        const val PLANT_DATA = "plant_data"
    }
}