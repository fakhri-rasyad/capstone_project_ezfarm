package com.c241ps093.ezfarm.ui.camera

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.c241ps093.ezfarm.R


class CameraFragment : Fragment() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    private fun allPermissionGranted() =
         ContextCompat.checkSelfPermission(requireActivity(), CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(activity, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(activity, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!allPermissionGranted()){
            requestPermissionLauncher.launch(CAMERA_PERMISSION)
        }
    }

    companion object {
        private const val CAMERA_PERMISSION = android.Manifest.permission.CAMERA
    }
}