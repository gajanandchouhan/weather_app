package com.gc.weatherapp.cityweather

import androidx.lifecycle.LiveData
import com.gc.weatherapp.common.DataWrapper

interface CityWeatherDataRepository {
    fun getCurrentWeather(city: String, unit: String): LiveData<DataWrapper>
    fun isLoading(): LiveData<Boolean>
}