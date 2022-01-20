package com.bilalqwatly.test.utils

import android.view.ViewGroup


object ViewHelper {
    fun enableAllViews(layout: ViewGroup, isEnable: Boolean) {
        layout.isEnabled = isEnable
        for (i in 0 until layout.childCount) {
            val child = layout.getChildAt(i)
            if (child is ViewGroup) {
                enableAllViews(child, isEnable)
            } else {
                child.isEnabled = isEnable
            }
        }
    } //


}