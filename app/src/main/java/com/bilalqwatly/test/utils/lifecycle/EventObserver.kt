package com.bilalqwatly.test.utils.lifecycle

import androidx.lifecycle.Observer

class EventObserver<T>(private val onChanged: OnChanged<T>) : Observer<Event<T>> {
    /**
     * Called when the data is changed.
     */
    override fun onChanged(event: Event<T>) {
        val data = event.dataOrNull
        if (data != null) onChanged.onChanged(data)
    }

    interface OnChanged<T> {
        fun onChanged(data: T)
    }
}