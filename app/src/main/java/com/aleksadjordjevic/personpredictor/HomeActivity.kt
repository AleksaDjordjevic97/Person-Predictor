package com.aleksadjordjevic.personpredictor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.LinearInterpolator
import com.aleksadjordjevic.personpredictor.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAnimations()
    }

    private fun setupAnimations()
    {
        setupLogoAnimation()
        setupPredictBtnAnimation()
    }

    private fun setupLogoAnimation()
    {
        binding.logoHome.animate().apply {
            interpolator = LinearInterpolator()
            duration = 1000
            translationYBy(calculateTranslationYForLogoAnimation())
            start()
        }
    }

    private fun setupPredictBtnAnimation()
    {
        binding.btnPredictHome.animate().apply {
            interpolator = LinearInterpolator()
            duration = 800
            alpha(1f)
            startDelay = 1100
            start()
        }
    }

    private fun calculateTranslationYForLogoAnimation():Float
    {
        val screenHeight = resources.displayMetrics.heightPixels
        val logoImageTopY = screenHeight*0.5f - screenHeight*0.45f/2f
        val newLogoImageTopY = screenHeight*0.1f

        return newLogoImageTopY - logoImageTopY
    }
}