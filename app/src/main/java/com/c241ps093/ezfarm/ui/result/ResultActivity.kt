package com.c241ps093.ezfarm.ui.result

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.c241ps093.ezfarm.data.database.Plant
import com.c241ps093.ezfarm.databinding.ActivityResultBinding
import com.c241ps093.ezfarm.makeToast
import com.c241ps093.ezfarm.ui.detail.DetailActivity
import com.c241ps093.ezfarm.viewmodel.factory.ViewModelFactory

class ResultActivity : AppCompatActivity() {
    private var _binding: ActivityResultBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plantUri = intent.getStringExtra(PLANT_URI)

        val viewModel = getViewModel(this)

        viewModel.postData(plantUri ?:  "", this)

        binding.apply {
            shapeableImageView.setImageURI(Uri.parse(plantUri))
        }

        viewModel.isLoading.observe(this){
            binding.pgBar.visibility = if(it) View.VISIBLE else View.INVISIBLE
        }
        viewModel.identificationData.observe(this){
            binding.apply {
                if (it != null) {
                    svData.visibility = View.VISIBLE
                    val data = it.resultWithPenanganan
                    plantName.text = data.namaTanaman
                    plantStatus.text = data.penyakit
                    deskripsiContent.text = data.deskripsi
                    gejalaContent.text = data.gejala.joinToString("\n")
                    penangananContent.text = data.penanganan.joinToString("\n")
                    pencegahanContent.text = data.pencegahan.joinToString("\n")
                }
            }
        }

        viewModel.errorMessage.observe(this){
            makeToast(this, it)
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getViewModel(appCompatActivity: AppCompatActivity) : ResultViewModel {
        val factory = ViewModelFactory.getInstance(appCompatActivity.application)
        return ViewModelProvider(appCompatActivity, factory)[ResultViewModel::class.java]
    }

    companion object {
        const val PLANT_URI = "plant_uri"
    }
}