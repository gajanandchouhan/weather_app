package com.gc.weatherapp.di

import com.gc.weatherapp.cityweather.CityWeatherDataRepository
import com.gc.weatherapp.cityweather.CityWeatherDataRepositoryImpl
import com.gc.weatherapp.cityweather.CityWeatherViewModel
import org.koin.dsl.module

val cityWeatherFragmentModule = module {
    single<CityWeatherDataRepository> { CityWeatherDataRepositoryImpl(get()) }
    single { CityWeatherViewModel(get()) }
}