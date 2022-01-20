package com.bilalqwatly.test.presentation.ui.auth.verification

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.bilalqwatly.test.BR
import com.bilalqwatly.test.R
import com.bilalqwatly.test.databinding.ActivityVerificationBinding
import com.bilalqwatly.test.presentation.ui.auth.login.LoginActivity
import com.bilalqwatly.test.presentation.ui.base.activity.MVVMActivity
import com.bilalqwatly.test.presentation.ui.main.MainActivity
import com.bilalqwatly.test.utils.NavigationUtil
import com.bilalqwatly.test.utils.constants.DataConstants
import com.google.firebase.auth.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VerificationActivity : MVVMActivity<VerificationViewModel, ActivityVerificationBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_verification

    override fun provideViewModel(): VerificationViewModel {
        return ViewModelProvider(this)[VerificationViewModel::class.java]
    }

    override val viewModelId: Int
        get() = BR.viewModel


    override fun onFetchParams() {
        super.onFetchParams()

        viewModel.storedVerificationId =
            intent.getStringExtra(DataConstants.STORED_VERIFICATION_ID)!!

        viewModel.countryCode =
            intent.getStringExtra(DataConstants.COUNTRY_CODE)!!

        viewModel.mobileNumber =
            intent.getStringExtra(DataConstants.MOBILE_NUMBER)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.auth = FirebaseAuth.getInstance()

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setListeners() {
        activityBinding.otpView.setOnTouchListener { v, _ ->
            focusAndShowKeyboard(v.context, activityBinding.otpView)
            true
        }
    }

    override fun setupBaseObservers() {
        super.setupBaseObservers()

        viewModel.isValidCode.observe(this, {

            if (it)
                signInWithPhoneAuthCredential(viewModel.credential)

        })


        viewModel.isSuccessfulVerification.observe(this, {

            if (it) {

                NavigationUtil.goToActivity(this, MainActivity::class.java, true)
            }
        })

    }

    private fun focusAndShowKeyboard(context: Context, editText: EditText) {
        editText.requestFocus()
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        viewModel.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    viewModel.updateSession(credential)

                } else {

                    // The verification code entered was invalid
                    showMessage(R.string.invalid_otp)

                    viewModel.stopLoadingButton()


                }
            }
    }


    override fun onBackPressed() {
        showAlertDialog(
            getString(R.string.warning),
            getString(R.string.are_you_sure_you_want_to_end_this_process)
        ) { _, _ ->
            finish()
        }
    }

}
