package com.vinay.truecaller.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Network

import android.os.Build

import android.net.NetworkCapabilities




object Util {

    fun isInternetConnected(context : Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            ?: return false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val cap = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
            return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val networks = cm.allNetworks
            for (n in networks) {
                val nInfo = cm.getNetworkInfo(n)
                if (nInfo != null && nInfo.isConnected) return true
            }
        } else {
            val networks = cm.allNetworkInfo
            for (nInfo in networks) {
                if (nInfo != null && nInfo.isConnected) return true
            }
        }

        return false
    }

}