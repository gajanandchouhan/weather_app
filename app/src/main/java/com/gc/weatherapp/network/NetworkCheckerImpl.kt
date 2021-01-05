package com.gc.weatherapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.util.Log

class NetworkCheckerImpl(context: Context) : NetworkStateChecker {
    private var isConnected = false
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isNetworkAvailable(): Boolean {
        return isConnected
    }

    init {
        cm.registerNetworkCallback(NetworkRequest.Builder().build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    isConnected = true
                    Log.v("NETOWRK", "available")
                }

                override fun onLost(network: Network) {
                    isConnected = false
                    Log.v("NETOWRK", "Not available")
                }
            })
    }
}