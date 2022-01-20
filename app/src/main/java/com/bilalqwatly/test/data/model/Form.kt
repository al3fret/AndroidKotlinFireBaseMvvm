package com.bilalqwatly.test.data.model

import androidx.lifecycle.MutableLiveData
import com.bilalqwatly.test.utils.FormType

class Form(type: FormType) {
    var text = MutableLiveData<String>()
    var textError = MutableLiveData<Int>()
    private var type: FormType
    fun getType(): FormType {
        return type
    }

    fun setType(type: FormType) {
        this.type = type
    }

    init {
        this.type = type
    }
}