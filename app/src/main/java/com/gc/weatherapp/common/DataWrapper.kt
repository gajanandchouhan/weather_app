package com.gc.weatherapp.common

data class DataWrapper(
    val request: Request,
    var response: Any? = null,
    var errorType: ErrorType? = null,
    var exception: Throwable? = null
)