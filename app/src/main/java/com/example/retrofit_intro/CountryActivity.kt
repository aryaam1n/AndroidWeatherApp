package com.example.retrofit_intro

import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofit_intro.constants.Constant
import com.example.retrofit_intro.currencydata.CurrencyInterface
import com.example.retrofit_intro.currencydata.Rates
import com.example.retrofit_intro.databinding.ActivityCountryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import kotlin.math.round
import kotlin.properties.Delegates

class CountryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        binding = ActivityCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCountryInfo()
        getCurrencyInfo()
    }

    //latin (spanish, portuguese), vietnamese, hebrew, arabic

    private fun getCurrencyInfo(){

        GlobalScope.launch(Dispatchers.IO) {
            val response = try{
                Retrofit.Builder()
                    .baseUrl(Constant.CurrencyURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CurrencyInterface::class.java)
                    .getExchangeRates(applicationContext.getString(R.string.currency_api_key))
            } catch (e: IOException){
                Toast.makeText(applicationContext, "APPLICATION ERROR: ${e.message}", Toast.LENGTH_SHORT).show()
                return@launch
            } catch (e: HttpException){
                Toast.makeText(applicationContext, "HTTP ERROR: ${e.message}", Toast.LENGTH_SHORT).show()
                return@launch
            }

            if (response.isSuccessful && response.body()!= null){
                withContext(Dispatchers.Main){
                    val currency = response.body()!!

                    binding.btnConverter.setOnClickListener {
                        val currencyTypeFrom = binding.spFrom.selectedItem.toString()
                        val currencyTypeTo = binding.spTo.selectedItem.toString()
                        val fromCurrencyRate = getRateForCurrency(currencyTypeFrom, currency.rates)
                        val toCurrencyRate = getRateForCurrency(currencyTypeTo, currency.rates)
                        val amount = Integer.parseInt(binding.etFrom.text.toString())

                        if (fromCurrencyRate != null && toCurrencyRate != null){
                            val result = round(amount * (toCurrencyRate / fromCurrencyRate))
                            binding.tvResult.text = "Result: $result $currencyTypeTo"
                        }

                    }

                }
            }
        }

    }

    private fun getCountryInfo(){
        val bundle = intent.extras
        var latitude by Delegates.notNull<Double>()
        var longitude by Delegates.notNull<Double>()

        if(bundle != null){
            latitude = bundle.getDouble("lat")
            longitude = bundle.getDouble("lon")
        }

        // declare the geocodeListener, as it requires Android API 33
        val geocodeListener = @RequiresApi(33) object : Geocoder.GeocodeListener {
            override fun onGeocode(addresses: MutableList<Address>) {
                // do something with the addresses list
                binding.CountryName.text = addresses[0].countryName
                binding.CountryCode.text = addresses[0].countryCode
            }
        }

        val geocoder = Geocoder(applicationContext)
        if (Build.VERSION.SDK_INT >= 33) {
            geocoder.getFromLocation(latitude, longitude, 1, geocodeListener)
        } else {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null) {
                binding.CountryName.text = addresses[0].countryName
                binding.CountryCode.text = addresses[0].countryCode
            }
            // For Android SDK < 33, the addresses list will be still obtained from the getFromLocation() method
        }
    }

    //learn more about what the question mark means and how null works in kotlin
    private fun getRateForCurrency(currency: String, rates: Rates): Double? = when (currency) {
        "USD" -> rates.USD
        "EUR" -> rates.EUR.toDouble()
        "GBP" -> rates.GBP
        "SEK" -> rates.SEK
        "NOK" -> rates.NOK
        "JPY" -> rates.JPY
        "SAR" -> rates.SAR
        "RUB" -> rates.RUB
        "RON" -> rates.RON
        "KWD" -> rates.KWD
        "INR" -> rates.INR
        else -> null
    }


}