package com.gc.weatherapp.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gc.weatherapp.network.NoInternetException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

abstract class DataRepository {
    private val isLoading = MutableLiveData<Boolean>()
    val liveData = MutableLiveData<DataWrapper>()

    fun <T : Any> doRequest(request: Request, call: Call<T>): LiveData<DataWrapper> {
        isLoading.value = true
        val dataWrapper = DataWrapper(request = request)
        call.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                isLoading.value = false
                dataWrapper.exception = t
                if (t is SocketTimeoutException)
                    dataWrapper.errorType = ErrorType.TIMEOUT_ERROR
                else if (t is NoInternetException)
                    dataWrapper.errorType = ErrorType.No_INTERNET
                else
                    dataWrapper.errorType = ErrorType.UNKNOWN_ERROR
                liveData.value = dataWrapper
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                isLoading.value = false
                if (response.isSuccessful) {
                    dataWrapper.response = response.body()
                } else {
                    if (response.code()==404)
                    dataWrapper.errorType = ErrorType.NOT_FOUND
                }
                liveData.value = dataWrapper
            }

        })

        return liveData
    }

    fun isLoadingData(): LiveData<Boolean> {
        return isLoading;
    }
}