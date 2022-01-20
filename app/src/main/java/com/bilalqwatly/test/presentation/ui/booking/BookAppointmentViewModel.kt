package com.bilalqwatly.test.presentation.ui.booking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bilalqwatly.test.data.model.*
import com.bilalqwatly.test.data.preferences.SharedPreferencesManager
import com.bilalqwatly.test.domain.usecase.BookAppointmentToFirebaseUseCase
import com.bilalqwatly.test.presentation.ui.base.viewmodel.BaseValidateViewModel
import com.bilalqwatly.test.presentation.ui.base.viewmodel.BaseViewModel
import com.bilalqwatly.test.utils.FormType
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class BookAppointmentViewModel @Inject constructor(
    session: SharedPreferencesManager,
    private val bookAppointmentToFirebaseUseCase: BookAppointmentToFirebaseUseCase
) : BaseValidateViewModel(session) {


    //Meeting Room Spinner Values
    var isMeetingRoomError = MutableLiveData<Boolean>()
    var selectedMeetingRoom = MutableLiveData<Item>()
    var isFirstItemMeetingRoom = MutableLiveData<Boolean>(true)
    var messageMeetingRoom: Form = Form(FormType.TEXT)
    var firstItemTitleMeetingRoom: String? = null


    //WorkDay Spinner Values
    var isWorkDayError = MutableLiveData<Boolean>()
    var selectedWorkDay = MutableLiveData<Item>()
    var isFirstItemWorkDay = MutableLiveData<Boolean>(true)
    var messageWorkDay: Form = Form(FormType.TEXT)
    var firstItemTitleWorkDay: String? = null

    //WorkDay Spinner Values
    var isPeriodError = MutableLiveData<Boolean>()
    var selectedPeriod = MutableLiveData<Item>()
    var isFirstItemPeriod = MutableLiveData(true)
    var messagePeriod: Form = Form(FormType.TEXT)
    var firstItemTitlePeriod: String? = null


    var isAddBookingSuccess = MutableLiveData<Boolean>()

    fun bookAnAppointment() {


        if (isFirstItemMeetingRoom.value!!) {
            return isMeetingRoomError.postValue(true)
        }

        if (isFirstItemWorkDay.value!!) {
            return isWorkDayError.postValue(true)
        }

        if (isFirstItemPeriod.value!!) {
            return isPeriodError.postValue(true)
        }

        bookAppointmentToFirebaseUseCase.execute(
            AppointmentModel(
                selectedMeetingRoom.value!!.content,
                selectedWorkDay.value!!.content,
                selectedPeriod.value!!.content,
                session.isoCode + session.mobileNumber

            )
        )

        isAddBookingSuccess.postValue(true)

    }


    init {

        forms = arrayOf(messageMeetingRoom, messageWorkDay)
        checkValidation()
    }
}