package com.c241ps093.ezfarm.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.c241ps093.ezfarm.R
import com.c241ps093.ezfarm.databinding.ActivityMainBinding
import com.c241ps093.ezfarm.ui.add.AddFragment
import com.c241ps093.ezfarm.ui.camera.CameraActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

        }

        val linearLayoutManager = LinearLayoutManager(this)

        binding.apply {
            homeRv.apply {
                layoutManager = linearLayoutManager
            }
            scanButton.setOnClickListener {
                val intent = Intent(this@HomeActivity, CameraActivity::class.java)
                startActivity(intent)
            }
            addFab.setOnClickListener {
                val fragmentManager = supportFragmentManager
                fragmentManager
                    .beginTransaction()
                    .add(R.id.main, AddFragment())
                    .addToBackStack(null)
                    .commit()

            }
        }

        val arrayList = arrayListOf<DummyData>()
        for(i in 1..10){
            arrayList.add(
                DummyData(
                    "Padi",
                    "20 Mei 2024",
                    "20 Aprils 2020",
                    "Seeding"
                )
            )
        }

        setUpRecyclerView(arrayList)
    }
    private fun setUpRecyclerView(
        plantList : List<DummyData>
    ){
        binding.apply {
            val adapter = HomeRecyclerViewAdapter(plantList)
            this.homeRv.adapter = adapter
        }
    }
}