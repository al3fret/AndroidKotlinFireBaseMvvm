package com.bilalqwatly.test.presentation.ui.auth.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bilalqwatly.test.BR
import com.bilalqwatly.test.R

import com.bilalqwatly.test.databinding.ActivityLoginBinding
import com.bilalqwatly.test.presentation.ui.auth.verification.VerificationActivity
import com.bilalqwatly.test.presentation.ui.base.activity.MVVMActivity
import com.bilalqwatly.test.utils.NavigationUtil
import com.bilalqwatly.test.utils.constants.DataConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.MultiFactorSession
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class LoginActivity : MVVMActivity<LoginViewModel, ActivityLoginBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_login

    override fun provideViewModel(): LoginViewModel {
        return ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override val viewModelId: Int
        get() = BR.viewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.auth = FirebaseAuth.getInstance()

    }


    // this method sends the verification code
    // and starts the callback of verification
    // which is implemented above in onCreate
    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(viewModel.auth!!)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Ti
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(viewModel.callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    override fun setupBaseObservers() {
        super.setupBaseObservers()
        viewModel.isValidLogin.observe(this,
            {
                if (it)
                    sendVerificationCode(
                        viewModel.countryCodeForm.text.value.toString() +
                                viewModel.mobileForm.text.value.toString()
                    )
            })

        viewModel.isCodeSent.observe(this, {

            if (it) {
                val bundle = Bundle()
                bundle.putString(
                    DataConstants.STORED_VERIFICATION_ID,
                    viewModel.storedVerificationId
                )
                bundle.putString(
                    DataConstants.MOBILE_NUMBER,
                    viewModel.mobileForm.text.value.toString()
                )
                bundle.putString(
                    DataConstants.COUNTRY_CODE,
                    viewModel.countryCodeForm.text.value.toString()
                )

                NavigationUtil.goToActivity(this, VerificationActivity::class.java, bundle)
            }

        })
    }
}