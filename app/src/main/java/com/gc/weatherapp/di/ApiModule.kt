package com.gc.weatherapp.di

import com.gc.weatherapp.network.*
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiInterfaceModule = module {
    fun provideApiInterface(networkStateChecker: NetworkStateChecker): ApiInterface {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        httpClient.addInterceptor(NetworkCheckInterceptor(networkStateChecker))
        httpClient.addInterceptor(HeaderInterceptor())
        httpClient.addInterceptor(logging)
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
        return retrofit.create(ApiInterface::class.java)

    }
    factory<NetworkStateChecker> { NetworkCheckerImpl(get()) }
    single { provideApiInterface(get()) }
}