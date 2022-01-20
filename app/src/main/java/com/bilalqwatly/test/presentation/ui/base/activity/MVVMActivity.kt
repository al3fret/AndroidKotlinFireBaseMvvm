package com.bilalqwatly.test.presentation.ui.base.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bilalqwatly.test.R
import com.bilalqwatly.test.presentation.ui.base.viewmodel.BaseViewModel

import com.bilalqwatly.test.utils.lifecycle.EventObserver
import com.bilalqwatly.test.utils.lifecycle.EventObserver.OnChanged

abstract class MVVMActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseActivity() {
    protected lateinit var activityBinding: DB
    protected lateinit var viewModel: VM
    protected abstract fun provideViewModel(): VM
    protected abstract val viewModelId: Int

    // Called to get params from intent's bundle
    protected open fun onFetchParams() {}
    override fun setView() {
        if (layoutId != 0) activityBinding = DataBindingUtil.setContentView(this, layoutId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
        onFetchParams()

        setupList()
        initToolbar()
        setupBaseObservers()
        setListeners()
    }

    protected open fun initToolbar() {}


    protected open fun setListeners() {}
    protected open fun setupList() {}
    private fun setupDataBinding() {
        viewModel = provideViewModel()
        activityBinding.setVariable(viewModelId, viewModel)
        activityBinding.lifecycleOwner = this
        activityBinding.executePendingBindings()
    }

    fun setStatusBarColor(color: Int) {
        window.statusBarColor = color
    }

    open fun setupBaseObservers() {

        viewModel.isError.observe(this, {

            if (it)
                showMessage(R.string.data_base_error)
        })

        viewModel.toastMessage.observe(
            this,
            EventObserver(object : OnChanged<String?> {
                override fun onChanged(data: String?) {
                    showMessage(data)
                }
            })
        )
        viewModel.toastMessageResource.observe(
            this,
            EventObserver(object : OnChanged<Int> {
                override fun onChanged(data: Int) {
                    showMessage(data)
                }
            })
        )
        viewModel.hideKeyboard.observe(
            this,
            EventObserver(object : OnChanged<Boolean> {
                override fun onChanged(data: Boolean) {
                    hideKeyboard()
                }
            })
        )

        viewModel.goBack.observe(this, { onBackPressed() })


    }

}
