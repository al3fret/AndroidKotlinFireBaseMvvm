package com.bilalqwatly.test.presentation.ui.booking

import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.bilalqwatly.test.BR
import com.bilalqwatly.test.R
import com.bilalqwatly.test.data.model.MeetingRoomModel
import com.bilalqwatly.test.data.model.PeriodModel
import com.bilalqwatly.test.data.model.WorkDayModel
import com.bilalqwatly.test.data.networkchecker.NetworkCheckerImpl
import com.bilalqwatly.test.databinding.ActivityBookAppointmentBinding
import com.bilalqwatly.test.databinding.SpinnerLayoutBinding
import com.bilalqwatly.test.presentation.ui.base.activity.MVVMActivity
import com.bilalqwatly.test.presentation.ui.base.adapter.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.floor

@AndroidEntryPoint
class BookAppointmentActivity : MVVMActivity<BookAppointmentViewModel,
        ActivityBookAppointmentBinding>() {

    private lateinit var meetingRoomItemAdapter: ItemAdapter
    private lateinit var workDayItemAdapter: ItemAdapter
    private lateinit var periodItemAdapter: ItemAdapter

    override val layoutId: Int
        get() = R.layout.activity_book_appointment

    override fun provideViewModel(): BookAppointmentViewModel {
        return ViewModelProvider(this)[BookAppointmentViewModel::class.java]
    }

    override val viewModelId: Int
        get() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.toolbarTitle.postValue(getString(R.string.book_an_appointment))

        setupSpinners()

    }


    private fun setupSpinners() {

        setupSpinnerMeetingRoom()
        setupSpinnerWorkDay()
        setupSpinnerPeriod()


    }

    private fun setupSpinnerMeetingRoom() {

        //first item title
        viewModel.firstItemTitleMeetingRoom = getString(R.string.meeting_room)

        meetingRoomItemAdapter = ItemAdapter(this, R.style.Text_Poppins_R_16_FFFFFF)
        activityBinding.spinnerMeetingRoom.spSpinner.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_spinner_season_dialog_download,
                null
            )
        )

        //spinnerMeetingRoom OnItemClick
        activityBinding.spinnerMeetingRoom.spSpinner.setOnItemClickListener { _, _, position, _ ->


            viewModel.selectedMeetingRoom.postValue(meetingRoomItemAdapter.getItem(position))


            if (position == 0) {
                viewModel.isFirstItemMeetingRoom.postValue(true)
                viewModel.messageMeetingRoom.textError.postValue(0)
                viewModel.isMeetingRoomError.postValue(true)

                //clear work Day spinner
                val firstDataWorkDay = WorkDayModel(viewModel.firstItemTitleWorkDay, null)
                val allDataWorkDay = listOf(firstDataWorkDay)
                viewModel.selectedWorkDay.postValue(firstDataWorkDay)
                workDayItemAdapter.submitData(allDataWorkDay)

                //clear period spinner
                val firstData = PeriodModel(viewModel.firstItemTitlePeriod, "", true)
                val allData = listOf(firstData)
                viewModel.selectedPeriod.postValue(firstData)
                periodItemAdapter.submitData(allData)


            } else {


                viewModel.isFirstItemMeetingRoom.postValue(false)
                viewModel.messageMeetingRoom.textError.postValue(0)
                viewModel.isMeetingRoomError.postValue(false)


                //init workDay spinner
                val firstDataWorkDay = WorkDayModel(viewModel.firstItemTitleWorkDay, null)
                val allDataWorkDay = (viewModel.session.getWorkDaysFromNameMeetingRoomList(
                    meetingRoomItemAdapter.getItem(position).content.toString()
                ))
                viewModel.selectedWorkDay.postValue(firstDataWorkDay)
                workDayItemAdapter.submitData(allDataWorkDay)
                workDayItemAdapter.insertItemInIndex(firstDataWorkDay, 0)

                //clear period spinner
                val firstDataPeriod = PeriodModel(viewModel.firstItemTitlePeriod, "", true)
                val allDataPeriod = listOf(firstDataPeriod)
                viewModel.selectedPeriod.postValue(firstDataPeriod)
                periodItemAdapter.submitData(allDataPeriod)

            }

            setSpinnerHeight(activityBinding.spinnerWorkDay, workDayItemAdapter)
            setSpinnerHeight(activityBinding.spinnerPeriod, periodItemAdapter)

            activityBinding.spinnerMeetingRoom.tvSpinner.text =
                meetingRoomItemAdapter.getItem(position).content
        }

        //spinnerMeetingRoom setOnClickListener
        activityBinding.spinnerMeetingRoom.vSpinner.setOnClickListener {
            activityBinding.spinnerMeetingRoom.spSpinner.showDropDown()
            activityBinding.spinnerMeetingRoom.tilSpinner.requestFocus()
        }
        activityBinding.spinnerMeetingRoom.vSpinner.requestFocus()


        //init data spinner
        val firstData = MeetingRoomModel(viewModel.firstItemTitleMeetingRoom, null)
        val allData = viewModel.session.getMeetingRoomsList()
        allData.add(0, firstData)
        viewModel.selectedMeetingRoom.postValue(firstData)
        meetingRoomItemAdapter.submitData(allData)

        // set adapter
        activityBinding.spinnerMeetingRoom.spSpinner.setAdapter(meetingRoomItemAdapter)


        //set height of spinner
        setSpinnerHeight(activityBinding.spinnerMeetingRoom, meetingRoomItemAdapter)


    }


    private fun setupSpinnerWorkDay() {

        //first item title
        viewModel.firstItemTitleWorkDay = getString(R.string.work_day)

        workDayItemAdapter = ItemAdapter(this, R.style.Text_Poppins_R_16_FFFFFF)
        activityBinding.spinnerWorkDay.spSpinner.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_spinner_season_dialog_download,
                null
            )
        )

        //spinner OnItemClick
        activityBinding.spinnerWorkDay.spSpinner.setOnItemClickListener { _, _, position, _ ->

            viewModel.selectedWorkDay.postValue(workDayItemAdapter.getItem(position))

            if (position == 0) {
                viewModel.isFirstItemWorkDay.postValue(true)
                viewModel.messageWorkDay.textError.postValue(0)
                viewModel.isWorkDayError.postValue(true)


                //clear period spinner
                val firstDataPeriod = PeriodModel(viewModel.firstItemTitlePeriod, "", true)
                val allDataPeriod = listOf(firstDataPeriod)
                viewModel.selectedPeriod.postValue(firstDataPeriod)
                periodItemAdapter.submitData(allDataPeriod)

            } else {
                viewModel.isFirstItemWorkDay.postValue(false)
                viewModel.messageWorkDay.textError.postValue(0)
                viewModel.isWorkDayError.postValue(false)


                //init period spinner
                val firstDataPeriod = PeriodModel(viewModel.firstItemTitlePeriod, "", true)
                val allDataPeriod = (viewModel.session.getPeriodsList(
                    viewModel.selectedMeetingRoom.value!!.content.toString(),
                    workDayItemAdapter.getItem(position).content.toString(),
                ))
                viewModel.selectedPeriod.postValue(firstDataPeriod)
                periodItemAdapter.submitData(allDataPeriod)
                periodItemAdapter.insertItemInIndex(firstDataPeriod, 0)



            }

            setSpinnerHeight(activityBinding.spinnerPeriod, periodItemAdapter)


            activityBinding.spinnerWorkDay.tvSpinner.text =
                workDayItemAdapter.getItem(position).content
        }

        //spinner setOnClickListener
        activityBinding.spinnerWorkDay.vSpinner.setOnClickListener {
            activityBinding.spinnerWorkDay.spSpinner.showDropDown()
            activityBinding.spinnerWorkDay.tilSpinner.requestFocus()
        }
        activityBinding.spinnerWorkDay.vSpinner.requestFocus()


        //init data spinner
        val firstData = WorkDayModel(viewModel.firstItemTitleWorkDay, null)
        val allData = listOf(firstData)

        viewModel.selectedWorkDay.postValue(firstData)
        workDayItemAdapter.submitData(allData)

        setSpinnerHeight(activityBinding.spinnerWorkDay, workDayItemAdapter)

        // set adapter
        activityBinding.spinnerWorkDay.spSpinner.setAdapter(workDayItemAdapter)


    }


    private fun setupSpinnerPeriod() {

        //first item title
        viewModel.firstItemTitlePeriod = getString(R.string.period)

        periodItemAdapter = ItemAdapter(this, R.style.Text_Poppins_R_16_FFFFFF)
        activityBinding.spinnerPeriod.spSpinner.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_spinner_season_dialog_download,
                null
            )
        )

        //spinner OnItemClick
        activityBinding.spinnerPeriod.spSpinner.setOnItemClickListener { _, _, position, _ ->

            viewModel.selectedPeriod.postValue(periodItemAdapter.getItem(position))


            if (position == 0) {
                viewModel.isFirstItemPeriod.postValue(true)
                viewModel.messagePeriod.textError.postValue(0)
                viewModel.isPeriodError.postValue(true)

            } else {
                viewModel.isFirstItemPeriod.postValue(false)
                viewModel.messagePeriod.textError.postValue(0)
                viewModel.isPeriodError.postValue(false)

            }
            activityBinding.spinnerPeriod.tvSpinner.text =
                periodItemAdapter.getItem(position).content
            viewModel.selectedPeriod.postValue(periodItemAdapter.getItem(position))

        }

        //spinner setOnClickListener
        activityBinding.spinnerPeriod.vSpinner.setOnClickListener {
            activityBinding.spinnerPeriod.spSpinner.showDropDown()
            activityBinding.spinnerPeriod.tilSpinner.requestFocus()
        }
        activityBinding.spinnerPeriod.vSpinner.requestFocus()


        //init data spinner
        val firstData = PeriodModel(viewModel.firstItemTitlePeriod, "", true)
        val allData = listOf(firstData)

        viewModel.selectedPeriod.postValue(firstData)
        periodItemAdapter.submitData(allData)

        setSpinnerHeight(activityBinding.spinnerPeriod, periodItemAdapter)

        // set adapter
        activityBinding.spinnerPeriod.spSpinner.setAdapter(periodItemAdapter)


    }

    private fun setSpinnerHeight(spinner: SpinnerLayoutBinding, adapter: ItemAdapter) {
        //set height of spinner
        spinner.spSpinner.dropDownHeight =
            (adapter.limitItems * floor(
                spinner.spSpinner.resources.getDimension(
                    adapter.getItem(0).size!!
                )
                    .toDouble()
            )).toInt()
    }


    override fun setupBaseObservers() {
        super.setupBaseObservers()
        viewModel.isAddBookingSuccess.observe(this, {

            if (it) {

                val networkCheckerImpl = NetworkCheckerImpl(this)
                if (networkCheckerImpl.isConnected) {
                    showMessage(getString(R.string.booking_added_successfully))
                } else {
                    showMessage(getString(R.string.booking_will_added_after_reconnect_to_network))

                }
                finish()
            }
        })
    }
}