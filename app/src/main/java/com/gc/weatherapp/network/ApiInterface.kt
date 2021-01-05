package com.gc.weatherapp.network

import com.gc.weatherapp.data.CurrentWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface ApiInterface {
    @GET("weather")
    fun getCurrentWeatherData(@QueryMap queres: HashMap<String, Any>): Call<CurrentWeatherResponse>
}