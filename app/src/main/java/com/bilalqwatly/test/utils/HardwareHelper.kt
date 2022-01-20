package com.bilalqwatly.test.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

/**
 * this class is used to get information about device hardware
 */
object HardwareHelper {

    @SuppressLint("HardwareIds")
    fun getAndroidId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

}