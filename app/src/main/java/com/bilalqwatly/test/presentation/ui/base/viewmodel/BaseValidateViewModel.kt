package com.bilalqwatly.test.presentation.ui.base.viewmodel

import com.bilalqwatly.test.data.model.Form
import com.bilalqwatly.test.data.preferences.SharedPreferencesManager
import com.bilalqwatly.test.utils.CheckClass.isValidEmail
import com.bilalqwatly.test.utils.CheckClass.isValidMobile
import com.bilalqwatly.test.utils.CheckClass.isValidPassword
import com.bilalqwatly.test.utils.CheckClass.isValidText
import com.bilalqwatly.test.utils.FormType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
open class BaseValidateViewModel @Inject constructor(
    session: SharedPreferencesManager
) : BaseViewModel(session = session) {
    private val PASSWORD_NOT_FOUND = -1
    lateinit var forms: Array<Form>
    val isValid: Boolean
        get() = if (forms.isNotEmpty()) {
            val passwordIndex = passwordIndex
            var isValid = true
            for (form in forms) {
                if (passwordIndex != PASSWORD_NOT_FOUND) {
                    setTextError(form, forms[passwordIndex])
                } else if (passwordIndex != PASSWORD_NOT_FOUND) {
                    setTextError(form, forms[passwordIndex])
                } else {
                    setTextError(form)
                }
                if (form.textError.value != 0) {
                    isValid = false
                }
            }
            isValid
        } else {
            true
        }

    fun checkValidation() {
        if (forms.isNotEmpty()) {
            val passwordIndex = passwordIndex
            for (i in forms.indices) {
                val index: Int = i
                forms[i].text.observeForever { text ->
                    if (text != null) {
                        if (passwordIndex != PASSWORD_NOT_FOUND) {
                            setTextError(forms[index], forms[passwordIndex])
                        } else if (passwordIndex != PASSWORD_NOT_FOUND) {
                            setTextError(forms[index], forms[passwordIndex])
                        } else {
                            setTextError(forms[index])
                        }
                    }
                }
            }
        }
    }

    private fun setTextError(vararg form: Form) {
        val CURRENT_FORM = 0
        val PASSWORD_INDEX = 1
        val CONFIRM_PASSWORD_INDEX = 2
        val isPasswordFound = form.size > 1
        val isConfirmPasswordFound = form.size > 2
        when (form[CURRENT_FORM].getType()) {
            FormType.TEXT -> form[CURRENT_FORM].textError.setValue(
                isValidText(
                    form[CURRENT_FORM].text.getValue()
                )
            )
            FormType.PASSWORD -> {
                form[CURRENT_FORM].textError
                    .postValue(isValidPassword(form[CURRENT_FORM].text.value.toString()))
            }
            FormType.EMAIL -> form[CURRENT_FORM].textError
                .postValue(isValidEmail(form[CURRENT_FORM].text.value.toString()))

            FormType.MOBILE_NUMBER -> form[CURRENT_FORM].textError
                .postValue(isValidMobile(form[CURRENT_FORM].text.value.toString()))
        }
    }

    private val passwordIndex: Int
        private get() {
            val isPassword = false
            var passwordIndex = PASSWORD_NOT_FOUND
            for (i in forms.indices) {
                if (forms[i].getType() === FormType.PASSWORD) {
                    passwordIndex = i
                    break
                }
            }
            return passwordIndex
        }
}