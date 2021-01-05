package com.gc.weatherapp.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class NetworkCheckInterceptor(private val networkStateChecker: NetworkStateChecker) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkStateChecker.isNetworkAvailable()) {
            throw NoInternetException()
        }
        val request = chain.request()
        return chain.proceed(request)
    }

}
