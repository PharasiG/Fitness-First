package com.example.fitness

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.fitness.databinding.ActivityWaterBinding

class WaterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWaterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onImageClick(view: View) {}
}