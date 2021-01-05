package com.gc.weatherapp.network

interface NetworkStateChecker {
    fun isNetworkAvailable(): Boolean
}
