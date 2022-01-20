package com.bilalqwatly.test.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    @SuppressLint("SimpleDateFormat")
    fun checkTimings(timeOne: String, timeTow: String): Boolean {
        val pattern = "HH:mm"
        val sdf = SimpleDateFormat(pattern)
        try {
            val dateOne: Date = sdf.parse(timeOne)
            val dateTow: Date = sdf.parse(timeTow)
            return dateOne.before(dateTow)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

}