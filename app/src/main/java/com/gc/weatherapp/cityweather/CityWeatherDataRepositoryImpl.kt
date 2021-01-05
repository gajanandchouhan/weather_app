package com.gc.weatherapp.cityweather

import androidx.lifecycle.LiveData
import com.gc.weatherapp.common.DataRepository
import com.gc.weatherapp.common.DataWrapper
import com.gc.weatherapp.common.Request
import com.gc.weatherapp.network.ApiInterface

class CityWeatherDataRepositoryImpl(private val apiInterface: ApiInterface) :
    CityWeatherDataRepository, DataRepository() {

    override fun getCurrentWeather(city: String, unit: String): LiveData<DataWrapper> {
        val queries = HashMap<String, Any>()
        queries["q"] = city
        queries["units"] = unit
        queries["appId"] = "fae7190d7e6433ec3a45285ffcf55c86"
        return doRequest(Request.CURRENT_WEATHER, apiInterface.getCurrentWeatherData(queries))
    }

    override fun isLoading(): LiveData<Boolean> {
        return isLoadingData()
    }
}