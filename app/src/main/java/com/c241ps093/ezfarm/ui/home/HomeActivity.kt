package com.c241ps093.ezfarm.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c241ps093.ezfarm.data.database.Plant
import com.c241ps093.ezfarm.databinding.ActivityMainBinding
import com.c241ps093.ezfarm.ui.add.AddFragment
import com.c241ps093.ezfarm.ui.camera.CameraActivity
import com.c241ps093.ezfarm.viewmodel.factory.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val linearLayoutManager = LinearLayoutManager(this)

        viewModel = getViewModel(this)

        binding.apply {
            homeRv.apply {
                layoutManager = linearLayoutManager
            }
            scanButton.setOnClickListener {
                val intent = Intent(this@HomeActivity, CameraActivity::class.java)
                startActivity(intent)
            }
            addFab.setOnClickListener {
                val addBottomSheetDialog: BottomSheetDialogFragment = AddFragment()
                addBottomSheetDialog.show(supportFragmentManager, "BSD Add Fragment")
            }
        }

        viewModel.getPlant().observe(this) {
            setUpRecyclerView(it)
        }
    }

    private fun setUpRecyclerView(
        plantList: List<Plant>
    ) {
        binding.apply {
            val adapter = HomeRecyclerViewAdapter(plantList)
            this.homeRv.adapter = adapter
        }
    }

    internal var addPlant: AddFragment.AddPlant = object : AddFragment.AddPlant {
        override fun addPlant(newPlant: Plant) {
            viewModel.addData(newPlant)
        }
    }

    private fun getViewModel(appCompatActivity: AppCompatActivity): HomeViewModel {
        val viewModelFactory =
            ViewModelFactory.getInstance(application = appCompatActivity.application)
        return ViewModelProvider(appCompatActivity, viewModelFactory)[HomeViewModel::class.java]
    }

}