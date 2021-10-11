package ru.marslab.casespace.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.marslab.casespace.R

private const val START_DELAY = 5000L

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycleScope.launch {
            delay(START_DELAY)
            val mainIntent = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
    }
}