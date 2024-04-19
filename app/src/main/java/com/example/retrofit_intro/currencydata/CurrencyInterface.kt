package com.example.retrofit_intro.currencydata

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyInterface {

    @GET("latest?")
    suspend fun getExchangeRates(
        @Query("access_key") apiKey : String,
    ) : Response<ExchangeRates>

}