package com.surehappiness.app.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkUtils {

    companion object{
        //네트워크 정보
        fun getNetworkInfo(context: Context): NetworkInfo {
            val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            return networkInfo
        }
    }
}