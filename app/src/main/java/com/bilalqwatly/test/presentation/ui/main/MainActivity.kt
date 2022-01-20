package com.bilalqwatly.test.presentation.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bilalqwatly.test.BR
import com.bilalqwatly.test.R
import com.bilalqwatly.test.databinding.ActivityMainBinding
import com.bilalqwatly.test.presentation.ui.base.activity.MVVMActivity
import com.bilalqwatly.test.presentation.ui.booking.BookAppointmentActivity
import com.bilalqwatly.test.presentation.ui.office.addmeetingroom.AddMeetingRoomActivity
import com.bilalqwatly.test.utils.NavigationUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : MVVMActivity<MainViewModel, ActivityMainBinding>() {


    override val layoutId: Int
        get() = R.layout.activity_main

    override fun provideViewModel(): MainViewModel {
        return ViewModelProvider(this)[MainViewModel::class.java]
    }

    override val viewModelId: Int
        get() = BR.viewModel


    override fun setupBaseObservers() {
        super.setupBaseObservers()

        viewModel.dataRecordList.observe(this, {

            for (record in it) {
                Log.w("Bilaloff ", record.id.toString() + "  " + record.receiverContactNumber)
            }
        })

        viewModel.isAddMeetingRoomClick.observe(this, {
            if (it) {

                NavigationUtil.goToActivity(this, AddMeetingRoomActivity::class.java)
            }
        })

        viewModel.isBookAnAppointmentClick.observe(this, {
            if (it) {
                NavigationUtil.goToActivity(this, BookAppointmentActivity::class.java)
            }
        })
    }
}