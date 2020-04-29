package com.android.albumlist.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

@Suppress("DEPRECATION")
class ConnectivityHelper {

    @SuppressLint("MissingPermission")
    fun isConnectedToNetwork(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

}