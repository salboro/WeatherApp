package com.example.weatherapp.presentation

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val animator =
            ObjectAnimator.ofFloat(findViewById(R.id.imageView2), View.ROTATION, 360f, 0f)
        animator.duration = 10000
        animator.repeatCount = Animation.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.start()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)
    }
}