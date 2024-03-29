package com.example.retrofit_intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import com.example.retrofit_intro.constants.Constant
import com.example.retrofit_intro.databinding.ActivityMainBinding
import com.example.retrofit_intro.weatherdata.CurrWeatherInterface
import com.google.android.gms.maps.OnMapReadyCallback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class MainActivity : AppCompatActivity() {

    //delay the initialization of variable until later
    private lateinit var binding: ActivityMainBinding
    private var city : String = "austin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(userQuery: String?): Boolean {

                if (userQuery!=null){
                    city = userQuery
                }

                getWeather(city)
                return true
            }

            override fun onQueryTextChange(userQuery: String?): Boolean {
                return false
            }

        })

        getWeather(city)
    }

    private fun getWeather(city:String){
        GlobalScope.launch(Dispatchers.IO) {
            val response = try{
                Retrofit.Builder()
                    .baseUrl(Constant.BaseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CurrWeatherInterface::class.java)
                    .getCurrentWeather(city, "imperial", applicationContext.getString(R.string.weather_api_key))
            } catch (e: IOException){
                Toast.makeText(applicationContext, "APPLICATION ERROR: ${e.message}", Toast.LENGTH_SHORT).show()
                return@launch
            } catch (e: HttpException){
                Toast.makeText(applicationContext, "HTTP ERROR: ${e.message}", Toast.LENGTH_SHORT).show()
                return@launch
            }

            //for some reason when I switched to the commented out part in the exception handler
            //the response.isSuccessful was not working


            if (response.isSuccessful && response.body()!= null){
                withContext(Dispatchers.Main){
                    val weatherNow = response.body()!!
                    binding.tvCity.text = weatherNow.name
                    binding.tvTemp.text = "${weatherNow.main.temp.toInt()}°F"
                    val iconUrl = "https://openweathermap.org/img/wn/${weatherNow.weather[0].icon}.png"
                    Picasso.get().load(iconUrl).into(binding.ivWeatherIcon)

                    binding.btnShowMap.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putDouble("lat", weatherNow.coord.lat)
                        bundle.putDouble("lon", weatherNow.coord.lon)
                        val intent = Intent(this@MainActivity, MapActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }
                }
            }
        }
    }



    /**
     * NEW CHANGES
     * put api keys in a constant/variable
     * added map functionality
     * added search city bar
     * data from one activity to another
     */

    /**
     * also make weather app dynamic to show different cities
     * also learn fragments
     * some type of apk file where we can use it on our phone
     */


    /** KOTLIN COROUTINES
     * synchronous programming is where we follow one task at a time
     * asynchronous - totally independent tasks
     * suspend function can only run within other suspend functions or within a coroutine
     */
}