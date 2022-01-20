package com.bilalqwatly.test.presentation.ui.office.addmeetingroom

import androidx.lifecycle.MutableLiveData
import com.bilalqwatly.test.data.model.Form
import com.bilalqwatly.test.data.model.MeetingRoomModel
import com.bilalqwatly.test.data.model.WorkDayModel
import com.bilalqwatly.test.data.preferences.SharedPreferencesManager
import com.bilalqwatly.test.domain.usecase.SaveMeetingRomToFirebaseUseCase
import com.bilalqwatly.test.presentation.ui.base.viewmodel.BaseValidateViewModel
import com.bilalqwatly.test.utils.FormType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MeetingRoomViewModel @Inject constructor(
    private val saveMeetingRomToFirebaseUseCase: SaveMeetingRomToFirebaseUseCase,
    session: SharedPreferencesManager
) : BaseValidateViewModel(session) {

    var isAddWorksDaysClick = MutableLiveData<Boolean>()
    var isAddMeetingRoomSuccess = MutableLiveData<Boolean>()
    var isErrorValidation = MutableLiveData<Boolean>()
    var dataWorksDays = MutableLiveData<List<WorkDayModel>>()
    var nameForm: Form = Form(FormType.TEXT)


    fun addWorksDays() = isAddWorksDaysClick.postValue(true)

    fun addMeetingRoom() {

        if (isValid && isValidMeetingRoom()) {
            hideKeyboard()
            saveMeetingRoomToFirebase()
        }
    }

    init {
        forms = arrayOf(nameForm)

    }


    private fun isValidMeetingRoom(): Boolean {

        if (dataWorksDays.value != null) {
            if (dataWorksDays.value!!.isEmpty()) {
                isErrorValidation.postValue(true)
                return false
            }
        } else {
            isErrorValidation.postValue(true)
            return false
        }


        for (workDay in dataWorksDays.value!!) {

            if (workDay.periodModels!!.isEmpty()) {
                isErrorValidation.postValue(true)
                return false
            }

            if (workDay.date!!.isEmpty()) {
                isErrorValidation.postValue(true)
                return false
            }

            for (period in workDay.periodModels!!) {

                if (period.from!!.isEmpty() || period.to!!.isEmpty()) {
                    isErrorValidation.postValue(true)
                    return false
                }
            }

        }


        return true;
    }


    private fun saveMeetingRoomToFirebase() {

        saveMeetingRomToFirebaseUseCase.execute(
            MeetingRoomModel(
                nameForm.text.value.toString(),
                dataWorksDays.value!!
            )
        )
        isAddMeetingRoomSuccess.postValue(true)
    }


}