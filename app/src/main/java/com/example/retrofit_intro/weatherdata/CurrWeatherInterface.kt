package com.example.retrofit_intro.weatherdata

import com.example.retrofit_intro.weatherdata.models.CurrentWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//interface for the current weather for a city
interface CurrWeatherInterface {

    @GET("weather?")
    suspend fun getCurrentWeather(
        @Query("q") cityName : String,
        @Query("units") units : String,
        @Query("appid") apiKey : String,
        ) : Response<CurrentWeather>

}