package com.example.retrofit_intro.weatherdata.models

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)