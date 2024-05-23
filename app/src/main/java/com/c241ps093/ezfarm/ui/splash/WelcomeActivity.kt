package com.c241ps093.ezfarm.ui.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.c241ps093.ezfarm.R
import com.c241ps093.ezfarm.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private var _binding : ActivityWelcomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimation()
    }

    private fun startAnimation(){
        val firstDotAnimation = getStartAnimation(binding.firstDot)
        val secondDotAnimation = getStartAnimation(binding.secondDot)
        val thirdDotAnimation = getStartAnimation(binding.thirdDot)

        val logoAnimation = ObjectAnimator.ofFloat(binding.welcomeLogo, "alpha", .5f, 1f)
            .apply {
                duration = 800
                interpolator = AccelerateInterpolator()
            }
        val backgroundAnimation = ValueAnimator.ofArgb(R.color.white, R.color.dartmouth_green)
            .apply {
                duration = 3000
                interpolator = AccelerateDecelerateInterpolator()
                addUpdateListener {
                    binding.main.background = ColorDrawable(getColor(it.animatedValue as Int))
                }
            }


        AnimatorSet().apply {
            playSequentially(firstDotAnimation, secondDotAnimation, thirdDotAnimation)
            play(logoAnimation).after(backgroundAnimation).after(thirdDotAnimation)
            start()
        }
    }

    private fun getStartAnimation(imageView:ImageView): AnimatorSet{
        val scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", .75f, 1f, .75f)
        val scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", .75f, 1f, .75f)
        val alpha = ObjectAnimator.ofFloat(imageView, "alpha", .5f, 1f, .5f)
        return AnimatorSet().apply{
            playTogether(scaleX ,scaleY, alpha)
            duration = 500
        }
    }

//    private fun getFinishedAnimation(imageView: ImageView):AnimatorSet{
//        val scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, .75f)
//        val scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", .75f, 1f)
//        val alpha = ObjectAnimator.ofFloat(imageView, "alpha", .5f, 1f)
//        return AnimatorSet().apply{
//            playTogether(scaleX ,scaleY, alpha)
//        }
//    }
}