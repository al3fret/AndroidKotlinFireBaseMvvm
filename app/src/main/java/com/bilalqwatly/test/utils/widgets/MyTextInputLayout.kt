package com.bilalqwatly.test.utils.widgets

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout


class MyTextInputLayout : TextInputLayout {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    fun setErrorFromRes(error: Int) {
        if (error != 0) {
            val errorMessage = context.getString(error)
            setError(errorMessage)
        }
    }
}
