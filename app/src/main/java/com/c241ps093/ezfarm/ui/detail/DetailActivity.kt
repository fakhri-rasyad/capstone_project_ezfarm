package com.c241ps093.ezfarm.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c241ps093.ezfarm.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private var _binding : ActivityDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}