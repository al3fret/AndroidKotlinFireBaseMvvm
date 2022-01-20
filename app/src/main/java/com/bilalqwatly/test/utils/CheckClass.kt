package com.bilalqwatly.test.utils

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import com.bilalqwatly.test.R


object CheckClass {
    fun isValidPassword(password: String): Int {
        if (TextUtils.isEmpty(password)) {
            return R.string.error_field_required
        }
        return if (password.length < 8) {
            R.string.password_must_be_at_least_6_characters
        } else 0
    }

    fun isValidEmail(email: String): Int {
        if (TextUtils.isEmpty(email)) {
            return R.string.error_field_required
        }
        return if (!Patterns.EMAIL_ADDRESS.matcher(email.trim { it <= ' ' }).matches()) {
            R.string.email_address_not_valid
        } else 0
    }

    fun isValidText(text: String?): Int {
        return if (text == null || TextUtils.isEmpty(text.trim { it <= ' ' })) {
            R.string.error_field_required
        } else 0
    }

    fun isValidEmailAddress(email: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidSearchText(editText: EditText, context: Context): Boolean {
        if (TextUtils.isEmpty(editText.text.toString()) || allCharactersSame(editText.text.toString())) {
            editText.error = context.resources.getString(R.string.error_field_required)
            editText.requestFocus()
            return false
        }
        return true
    }

    fun isValidMobile(phone: String): Int {
        if (!Patterns.PHONE.matcher(phone).matches()) {
            return R.string.error_field_required
        }
        return if (phone.length in 4..14) {
            0
        } else {
            return R.string.mobile_number_must_between_4_to_14

        }
    }

    private fun allCharactersSame(s: String): Boolean {
        val n = s.length
        for (i in 0 until n) if (s[i] != ' ' && s[i] != '\n') return false
        return true
    }

    fun isValidConfirmPassword(password: String, confirmPassword: String?): Int {
        if (TextUtils.isEmpty(password)) {
            return R.string.error_field_required
        }
        return if (password.length < 6) {
            R.string.password_must_be_at_least_6_characters
        } else 0
    }
}