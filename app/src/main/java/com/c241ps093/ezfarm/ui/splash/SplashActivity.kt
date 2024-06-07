package com.c241ps093.ezfarm.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.c241ps093.ezfarm.R
import com.c241ps093.ezfarm.databinding.ActivitySplashBinding
import com.c241ps093.ezfarm.ui.home.HomeActivity
import com.c241ps093.ezfarm.ui.welcome.WelcomeActivity
import com.c241ps093.ezfarm.viewmodel.SplashViewModel
import com.c241ps093.ezfarm.viewmodel.factory.ViewModelFactory
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        viewModel = getViewModel(this)
        setContentView(binding.root)

        binding.main.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                if (currentId == R.id.show_logo) {

                    if (viewModel.checkHasUsedApp()) {
                        val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        lifecycleScope.launch { viewModel.setHasUsedApp() }
                        val intent = Intent(this@SplashActivity, WelcomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getViewModel(appCompatActivity: AppCompatActivity): SplashViewModel {
        val viewModelFactory = ViewModelFactory.getInstance(appCompatActivity.application)
        return ViewModelProvider(appCompatActivity, viewModelFactory)[SplashViewModel::class.java]
    }
}