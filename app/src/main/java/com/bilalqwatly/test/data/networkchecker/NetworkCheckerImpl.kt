package com.bilalqwatly.test.data.networkchecker

import android.content.Context
import android.net.ConnectivityManager
import com.bilalqwatly.test.data.networkchecker.INetworkChecker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkCheckerImpl @Inject constructor(@ApplicationContext context: Context) :
    INetworkChecker {
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    override val isConnected: Boolean
        get() {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnectedOrConnecting
        }
}