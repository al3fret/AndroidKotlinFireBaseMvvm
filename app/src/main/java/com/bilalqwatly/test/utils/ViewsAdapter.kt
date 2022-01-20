package com.bilalqwatly.test.utils

import android.app.Activity
import android.app.Application
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.bilalqwatly.test.R
import com.bilalqwatly.test.data.model.Item
import com.bilalqwatly.test.presentation.ui.base.adapter.BaseListAdapter
import com.bilalqwatly.test.presentation.ui.base.adapter.ItemAdapter
import com.bilalqwatly.test.utils.widgets.MyTextInputLayout
import com.mukesh.OnOtpCompletionListener
import com.mukesh.OtpView
import java.io.Serializable
import kotlin.math.floor
import kotlin.math.roundToInt
import java.lang.Object as Ser


/**
 * This class is used to fill data in the Adapter and cache an image
 */
object ViewsAdapter {


    @JvmStatic
    @BindingAdapter("enableAllViews")
    fun enableAllViews(view: View?, isEnable: Boolean) {
        ViewHelper.enableAllViews((view as ViewGroup?)!!, isEnable)
    }


    @JvmStatic
    @BindingAdapter("buttonLoading")
    fun buttonLoading(circularProgressButton: CircularProgressButton, isLoading: Boolean) {
        if (isLoading) {
            circularProgressButton.startAnimation()
        } else {
            circularProgressButton.revertAnimation()
        }
    }

    @JvmStatic
    @BindingAdapter("onError")
    fun onError(textInputLayout: MyTextInputLayout, errorMessage: Int?) {
        if (errorMessage != null && errorMessage != 0) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.setErrorFromRes(errorMessage)
        } else {
            textInputLayout.isErrorEnabled = false
            textInputLayout.error = null
        }
    }

    @JvmStatic
    @BindingAdapter("otpCompletion")
    fun OtpCompletion(otpView: OtpView, isComplete: MutableLiveData<Boolean?>) {
        otpView.setOtpCompletionListener { isComplete.value = true }
    }

    @JvmStatic
    @BindingAdapter("loadDataRecycler")
    fun loadData(recyclerView: RecyclerView, data: List<Serializable>?) {
        val adapter = recyclerView.adapter
        if (adapter != null) {
            if (adapter is BaseListAdapter<*, *>) {
                adapter.submitData(data)
            }
        }
    }




}