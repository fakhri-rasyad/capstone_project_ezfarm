package com.c241ps093.ezfarm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.c241ps093.ezfarm.databinding.ActivityMainBinding
import com.c241ps093.ezfarm.ui.add.AddFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            bottomNavView.background = null
            bottomNavView.menu.getItem(1).isEnabled = false
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
            val navController = navHostFragment.navController
            bottomNavView.setupWithNavController(navController)
            addFab.setOnClickListener {
                val fragmentManager = supportFragmentManager
                fragmentManager
                    .beginTransaction()
                    .add(R.id.main, AddFragment())
                    .addToBackStack(null)
                    .commit()

            }
        }
    }
}