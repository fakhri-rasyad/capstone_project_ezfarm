package com.c241ps093.ezfarm.ui.result

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c241ps093.ezfarm.data.database.Plant
import com.c241ps093.ezfarm.databinding.ActivityResultBinding
import com.c241ps093.ezfarm.ui.detail.DetailActivity

class ResultActivity : AppCompatActivity() {
    private var _binding: ActivityResultBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plantUri = intent.getStringExtra(PLANT_URI)

        binding.apply {
            shapeableImageView.setImageURI(Uri.parse(plantUri))
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val PLANT_URI = "plant_uri"
    }
}