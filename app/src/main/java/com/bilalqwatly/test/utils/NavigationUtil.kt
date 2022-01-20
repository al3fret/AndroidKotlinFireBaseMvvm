package com.bilalqwatly.test.utils

import android.content.Context
import kotlin.jvm.JvmOverloads
import android.content.Intent
import android.os.Bundle



/**
 * this class is used to navigate to another Fragment or start a new Activity
 */
object NavigationUtil {
    @JvmOverloads
    fun goToActivity(
        context: Context,
        intent: Intent,
        isFinish: Boolean = false,
        bundle: Bundle? = null
    ) {
        var intentLocal = intent
        if (null != bundle) intentLocal = intent.putExtras(bundle)
        intentLocal.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (isFinish) intentLocal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }

    @JvmOverloads
    fun goToActivity(
        context: Context,
        target: Class<*>?,
        isFinish: Boolean = false,
        bundle: Bundle? = null
    ) {
        goToActivity(context, Intent(context, target), isFinish, bundle)
    }

    fun goToActivity(
        context: Context,
        intent: Intent,
        bundle: Bundle?
    ) {
        goToActivity(context, intent, false, bundle)
    }

    fun goToActivity(
        context: Context,
        target: Class<*>?,
        bundle: Bundle?
    ) {
        goToActivity(context, target, false, bundle)
    }




}