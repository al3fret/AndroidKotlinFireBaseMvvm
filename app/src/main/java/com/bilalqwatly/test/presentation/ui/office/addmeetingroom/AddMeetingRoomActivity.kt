package com.bilalqwatly.test.presentation.ui.office.addmeetingroom


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bilalqwatly.test.BR
import com.bilalqwatly.test.R
import com.bilalqwatly.test.data.model.PeriodModel
import com.bilalqwatly.test.data.model.WorkDayModel
import com.bilalqwatly.test.data.networkchecker.NetworkCheckerImpl
import com.bilalqwatly.test.databinding.ActivityAddMeetingRoomBinding
import com.bilalqwatly.test.presentation.ui.base.activity.MVVMActivity
import com.bilalqwatly.test.presentation.ui.callback.OnAddPeriodClickListener
import com.bilalqwatly.test.presentation.ui.callback.OnItemClickListener
import com.bilalqwatly.test.presentation.ui.callback.OnSetTimePeriodClickListener
import com.bilalqwatly.test.presentation.ui.office.addmeetingroom.adapter.WorkDayAdapter
import com.bilalqwatly.test.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddMeetingRoomActivity : MVVMActivity<MeetingRoomViewModel, ActivityAddMeetingRoomBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_add_meeting_room


    private val onRemovePeriodItemClickListener: OnItemClickListener<PeriodModel> =
        object : OnItemClickListener<PeriodModel> {
            override fun onClick(item: PeriodModel, position: Int) {

                val periodModels =
                    viewModel.dataWorksDays.value?.get(position)?.periodModels?.minus(item)
                viewModel.dataWorksDays.value?.get(position)?.periodModels = periodModels
                viewModel.dataWorksDays.value = viewModel.dataWorksDays.value!!
            }
        }

    private val onRemoveWorkDayItemClickListener: OnItemClickListener<WorkDayModel> =
        object : OnItemClickListener<WorkDayModel> {
            override fun onClick(item: WorkDayModel, position: Int) {
                viewModel.dataWorksDays.value = viewModel.dataWorksDays.value?.minus(item)

            }
        }

    private val onAddPeriodItemClickListener: OnAddPeriodClickListener =
        object : OnAddPeriodClickListener {
            override fun onClickAddPeriod(position: Int) {


                val periodModels =
                    viewModel.dataWorksDays.value?.get(position)?.periodModels?.plusElement(
                        PeriodModel(
                            "",
                            "",
                            false
                        )
                    )
                viewModel.dataWorksDays.value?.get(position)?.periodModels = periodModels
                viewModel.dataWorksDays.value = viewModel.dataWorksDays.value!!


            }
        }


    private val onSetDateItemClickListener: OnItemClickListener<WorkDayModel> =
        object : OnItemClickListener<WorkDayModel> {
            override fun onClick(item: WorkDayModel, position: Int) {

                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)


                val datePickerDialog =
                    DatePickerDialog(context, { _, year, monthOfYear, dayOfMonth ->
                        val month = monthOfYear + 1
                        val selectedDate = "$dayOfMonth-$month-$year"
                        selectedDate.also { viewModel.dataWorksDays.value!![position].date = it }
                        viewModel.dataWorksDays.value = viewModel.dataWorksDays.value!!
                    }, year, month, day)

                datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000;
                datePickerDialog.show()


            }


        }


    private val onSetFromClickListener: OnSetTimePeriodClickListener =
        object : OnSetTimePeriodClickListener {
            override fun onSetTime(positionWorkDay: Int, positionPeriod: Int) {

                val c = Calendar.getInstance()
                val hourOfDay = c.get(Calendar.HOUR_OF_DAY)
                val minute = c.get(Calendar.MINUTE)

                TimePickerDialog(context, { _, hour, minute ->

                    val selectedTime = "$hour:$minute"
                    selectedTime.also {
                        viewModel.dataWorksDays.value!![positionWorkDay].periodModels!![positionPeriod].from =
                            it
                    }
                    "".also {
                        viewModel.dataWorksDays.value!![positionWorkDay].periodModels!![positionPeriod].to =
                            it
                    }
                    viewModel.dataWorksDays.value = viewModel.dataWorksDays.value!!

                }, hourOfDay, minute, true).show()


            }


        }

    private val onSetToClickListener: OnSetTimePeriodClickListener =
        object : OnSetTimePeriodClickListener {
            override fun onSetTime(positionWorkDay: Int, positionPeriod: Int) {

                val fromTime =
                    viewModel.dataWorksDays.value!![positionWorkDay].periodModels!![positionPeriod].from

                if (fromTime != null && fromTime.isNotEmpty()) {
                    val c = Calendar.getInstance()
                    val hourOfDay = c.get(Calendar.HOUR_OF_DAY)
                    val minute = c.get(Calendar.MINUTE)

                    TimePickerDialog(context, { _, hour, minute ->

                        val toTime = "$hour:$minute"
                        val fromTime =
                            viewModel.dataWorksDays.value!![positionWorkDay].periodModels!![positionPeriod].from!!


                        if (DateUtils.checkTimings(fromTime, toTime)) {
                            toTime.also {
                                viewModel.dataWorksDays.value!![positionWorkDay].periodModels!![positionPeriod].to =
                                    it
                            }
                            viewModel.dataWorksDays.value = viewModel.dataWorksDays.value!!
                        } else {

                            showMessage(getString(R.string.select_valid_rang_time))
                        }

                    }, hourOfDay, minute, true).show()


                } else {
                    showMessage(getString(R.string.select_from_before))

                }

            }
        }


    override fun provideViewModel(): MeetingRoomViewModel {
        return ViewModelProvider(this)[MeetingRoomViewModel::class.java]
    }

    override val viewModelId: Int
        get() = BR.viewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.toolbarTitle.postValue(getString(R.string.add_meeting_room))
        activityBinding.fabWorksDays.setColorFilter(Color.WHITE);

    }

    override fun setupBaseObservers() {
        super.setupBaseObservers()

        viewModel.isAddWorksDaysClick.observe(this, {

            if (it) {
                val workDayModel = WorkDayModel("", listOf(PeriodModel("", "", true)))
                viewModel.dataWorksDays.value =
                    viewModel.dataWorksDays.value?.plus(workDayModel)
                        ?: listOf(workDayModel)
            }

        })

        viewModel.isErrorValidation.observe(this, {

            if (it) {
                showMessage(getString(R.string.please_fill_all_input))
            }
        })
        viewModel.isAddMeetingRoomSuccess.observe(this, {

            if (it) {

                val networkCheckerImpl = NetworkCheckerImpl(this)
                if (networkCheckerImpl.isConnected) {
                    showMessage(getString(R.string.meeting_room_added_successfully))
                } else {
                    showMessage(getString(R.string.meeting_room_will_added_after_reconnect_to_network))

                }
                finish()
            }
        })
    }

    override fun setupList() {
        adapter = WorkDayAdapter(
            this,
            onRemoveWorkDayItemClickListener,
            onRemovePeriodItemClickListener,
            onAddPeriodItemClickListener,
            onSetDateItemClickListener,
            onSetFromClickListener,
            onSetToClickListener,
        )
        activityBinding.rvList.adapter = adapter
        activityBinding.rvList.itemAnimator = null
    }


}