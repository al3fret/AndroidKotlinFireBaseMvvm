package com.bilalqwatly.test.presentation.ui.auth.verification

import androidx.lifecycle.MutableLiveData
import com.bilalqwatly.test.data.preferences.SharedPreferencesManager
import com.bilalqwatly.test.presentation.ui.base.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(session: SharedPreferencesManager) :
    BaseViewModel(session) {

    var isLoadingButton = MutableLiveData<Boolean>()
    var isComplete = MutableLiveData<Boolean>()
    var isSuccessfulVerification = MutableLiveData<Boolean>()
    var isValidCode = MutableLiveData<Boolean>()
    var code = MutableLiveData("")
    lateinit var storedVerificationId: String
    lateinit var mobileNumber: String
    lateinit var countryCode: String
    lateinit var credential: PhoneAuthCredential

    // create instance of firebase auth
    lateinit var auth: FirebaseAuth


    fun startLoadingButton() {
        isLoadingButton.postValue(true)
    }

    fun stopLoadingButton() {
        isLoadingButton.postValue(false)
    }

    fun sendVerificationCode() {
        startLoadingButton()
        hideKeyboard()

        credential = PhoneAuthProvider.getCredential(
            storedVerificationId, code.value.toString()
        )
        isValidCode.postValue(true)

    }


    fun isValid(): Boolean {
        return code.value!!.length == 6
    }


    fun updateSession(credential: PhoneAuthCredential) {

        session.isAuthenticated = true
        session.mobileNumber = mobileNumber
        session.isoCode = countryCode
        isSuccessfulVerification.postValue(true)


    }
}


