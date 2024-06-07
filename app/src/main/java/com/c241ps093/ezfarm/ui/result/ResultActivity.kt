package com.c241ps093.ezfarm.ui.result

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c241ps093.ezfarm.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private var _binding: ActivityResultBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

    }
}