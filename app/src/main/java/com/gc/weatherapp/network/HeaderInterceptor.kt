package com.gc.weatherapp.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (chain.request().url.toString().contains("X-Amz-SignedHeaders=host")) {
            return chain.proceed(chain.request())
        }
        val request = chain.request()
            .newBuilder().header("Authorization", "Bearer ").build()
        return chain.proceed(request)
    }
}