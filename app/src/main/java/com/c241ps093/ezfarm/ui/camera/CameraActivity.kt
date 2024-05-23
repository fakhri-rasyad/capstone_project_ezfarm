package com.c241ps093.ezfarm.ui.camera

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.c241ps093.ezfarm.R
import com.c241ps093.ezfarm.databinding.ActivityCameraBinding
import com.c241ps093.ezfarm.databinding.FragmentCameraBinding

class CameraActivity : AppCompatActivity() {

    private var cameraSelector : CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var _binding : ActivityCameraBinding? = null
    private val binding get() = _binding!!

    private fun allPermissionGranted() =
        ContextCompat.checkSelfPermission(this,
            CAMERA_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview
                )
            } catch (exc: Exception) {
                Toast.makeText(
                    this,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       _binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(!allPermissionGranted()){
            requestPermissionLauncher.launch(CAMERA_PERMISSION)
        }
        startCamera()

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.cameraButton.setOnClickListener {
            val scaleX = ObjectAnimator.ofFloat(it, "scaleX", 1f, 1.5f, 1f)
            val scaleY = ObjectAnimator.ofFloat(it, "scaleY", 1f, 1.5f, 1f)

            AnimatorSet().apply {
                playTogether(scaleX, scaleY)
                duration = 300
                start()
            }
        }
    }

    companion object {
        private const val CAMERA_PERMISSION = android.Manifest.permission.CAMERA
    }
}