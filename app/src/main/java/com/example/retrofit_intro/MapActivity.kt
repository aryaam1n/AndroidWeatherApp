package com.example.retrofit_intro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofit_intro.R
import com.example.retrofit_intro.databinding.ActivityMainBinding
import com.example.retrofit_intro.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        if (bundle != null){
            binding.tvLat.text = "Lat = ${bundle.getDouble("lat")}"
            binding.tvLon.text = "Lon = ${bundle.getDouble("lon")}"
        }

    }


}