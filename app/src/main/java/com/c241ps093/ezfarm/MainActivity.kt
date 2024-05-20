package com.c241ps093.ezfarm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c241ps093.ezfarm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            this.bottomNavView.background = null
            this.bottomNavView.menu.getItem(2).isEnabled = false
        }
    }
}