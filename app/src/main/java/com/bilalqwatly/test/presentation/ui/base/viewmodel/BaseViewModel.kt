package com.bilalqwatly.test.presentation.ui.base.viewmodel;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bilalqwatly.test.data.preferences.SharedPreferencesManager
import com.bilalqwatly.test.utils.lifecycle.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
open class BaseViewModel @Inject constructor(
    var session: SharedPreferencesManager
) : ViewModel() {

    val goBack = MutableLiveData<Boolean>()

    var isLoading = MutableLiveData(false)
    var isError = MutableLiveData(false)
    var errorMessage = MutableLiveData("")
    val _toastMessage = MutableLiveData<Event<String?>>()
    var toastMessage: LiveData<Event<String?>> = _toastMessage
    val _toastMessageResource = MutableLiveData<Event<Int>>()
    var toastMessageResource: LiveData<Event<Int>> = _toastMessageResource
    val _hideKeyboard = MutableLiveData<Event<Boolean>>()
    var hideKeyboard: LiveData<Event<Boolean>> = _hideKeyboard

    var toolbarTitle = MutableLiveData("")

    protected fun showMessage(message: String?) {
        _toastMessage.postValue(Event(message))
    }


    private fun showMessage(stringId: Int) {
        _toastMessageResource.postValue(Event(stringId))
    }

//    protected fun showMessage(error: Throwable) {
//        if (error.message != null) showMessage(error.message) else
//            showMessage(ExceptionFactory.string)
//    }

    open fun goBack() {
        goBack.postValue(true)
    }

    protected fun showError() {
        isError.postValue(true)
        stopLoading()
    }

    protected fun hideKeyboard() {
        _hideKeyboard.postValue(Event(true))
    }

    private fun startLoading(loading: Boolean) {

        isError.postValue(false)
        isLoading.postValue(loading)
    }


    private fun stopLoading() {
        isLoading.postValue(false)
    }


    open fun setToolbarTitle(title: String) {
        toolbarTitle.postValue(title)
    }


    open fun fetchData(loading: Boolean) {
        startLoading(loading)
    }

    private fun loadData(loading: Boolean) {
        if (isLoading.value!!) return
        fetchData(loading)
    }


}

