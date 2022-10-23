package com.sweet.cloves.robbyassessment.ui

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.sweet.cloves.robbyassessment.databinding.ActivitySplashBinding
import com.sweet.cloves.robbyassessment.ui.main.screen.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater);
        val view = binding.root
        setContentView(view)
        goToMain()
    }

    private fun goToMain(){
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}