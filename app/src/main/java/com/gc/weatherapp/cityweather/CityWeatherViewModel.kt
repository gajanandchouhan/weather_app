package com.gc.weatherapp.cityweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gc.weatherapp.common.DataWrapper

class CityWeatherViewModel(private val cityWeatherDataRepository: CityWeatherDataRepository) :
    ViewModel() {
    fun getCurrentWeather(city: String, unit: String = "metric"): LiveData<DataWrapper> {
        return cityWeatherDataRepository.getCurrentWeather(city, unit)
    }

    fun isLoading(): LiveData<Boolean> {
        return cityWeatherDataRepository.isLoading()
    }
}