package com.bilalqwatly.test.presentation.ui.auth.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bilalqwatly.test.data.model.Form
import com.bilalqwatly.test.data.preferences.SharedPreferencesManager
import com.bilalqwatly.test.presentation.ui.base.viewmodel.BaseValidateViewModel
import com.bilalqwatly.test.utils.FormType
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(session: SharedPreferencesManager) :
    BaseValidateViewModel(session) {

    var mobileForm: Form = Form(FormType.MOBILE_NUMBER)
    var countryCodeForm: Form = Form(FormType.TEXT)
    var isLoadingButton = MutableLiveData<Boolean>()
    var isValidLogin = MutableLiveData<Boolean>()
    var isCodeSent = MutableLiveData<Boolean>()


    // create instance of firebase auth
     var auth: FirebaseAuth? = null

    // we will use this to match the sent otp from firebase
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    private fun startLoadingButton() {
        isLoadingButton.postValue(true)
    }

    private fun stopLoadingButton() {
        isLoadingButton.postValue(false)

    }


    fun postLogin() {

        if (isValid) {
            startLoadingButton()
            hideKeyboard()
            isValidLogin.postValue(true)
        }
    }


    init {
        countryCodeForm.text.postValue("+971")
        forms = arrayOf(mobileForm, countryCodeForm)


        // Callback function for Phone Auth
        callbacks =
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                // This method is called when the verification is completed
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                    Log.d("GFG", "onVerificationCompleted Success")
                }

                // Called when verification is failed add log statement to see the exception
                override fun onVerificationFailed(e: FirebaseException) {
                    stopLoadingButton()
                    showMessage(e.message!!)
                    Log.d("GFG", "$e.message!!")

                }

                // On code is sent by the firebase this method is called
                // in here we start a new activity where user can enter the OTP
                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    stopLoadingButton()

                    Log.d("GFG", "onCodeSent: $verificationId")
                    storedVerificationId = verificationId
                    resendToken = token
                    isCodeSent.postValue(true)

                    // Start a new activity using intent
                    // also send the storedVerificationId using intent
                    // we will use this id to send the otp back to firebase


                }
            }

    }


}

