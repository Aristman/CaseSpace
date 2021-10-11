package ru.marslab.casespace.ui

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ru.marslab.casespace.R

private const val START_DELAY = 5000L

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        findViewById<ImageView>(R.id.splash_image).animate()
            .rotationXBy(750f)
            .rotationYBy(750f)
            .setInterpolator(LinearInterpolator())
            .setDuration(START_DELAY)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {}

                override fun onAnimationEnd(p0: Animator?) {
                    val mainIntent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }

                override fun onAnimationCancel(p0: Animator?) {}

                override fun onAnimationRepeat(p0: Animator?) {}

            })
    }
}