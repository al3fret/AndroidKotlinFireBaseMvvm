package com.bilalqwatly.test.utils.lifecycle

/**
 * Data Wrapper that can be handled only once
 * @param <T> data type
</T> */
class Event<T>(private val data: T) {
    private var isHandled = false
    val dataOrNull: T?
        get() {
            if (isHandled) return null
            isHandled = true
            return data
        }
}