package com.bilalqwatly.test.presentation.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.collect
import androidx.lifecycle.viewModelScope
import com.bilalqwatly.test.data.model.RecordModel
import com.bilalqwatly.test.data.model.StateModel
import com.bilalqwatly.test.data.preferences.SharedPreferencesManager
import com.bilalqwatly.test.domain.usecase.GetAllRecordsUseCase
import com.bilalqwatly.test.domain.usecase.GetMeetingRoomFromFirebaseUseCase
import com.bilalqwatly.test.domain.usecase.SaveRecordUseCase
import com.bilalqwatly.test.domain.usecase.SaveMeetingRomToFirebaseUseCase
import com.bilalqwatly.test.presentation.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    session: SharedPreferencesManager,
    private val saveRecordUseCase: SaveRecordUseCase,
    private val getMeetingRoomFromFirebaseUseCase: GetMeetingRoomFromFirebaseUseCase,
    private val getAllRecordsUseCase: GetAllRecordsUseCase
) : BaseViewModel(session) {


    var dataRecordList = MutableLiveData<List<RecordModel>>()
    var isAddMeetingRoomClick = MutableLiveData<Boolean>()
    var isBookAnAppointmentClick = MutableLiveData<Boolean>()

    // save data to local database after generating the QR code.
    @RequiresApi(Build.VERSION_CODES.M)
    fun saveRecord() =

        viewModelScope.launch {
            val currentTimestamp = System.currentTimeMillis()
            val record = RecordModel(
                0,
                session.isoCode!! + session.mobileNumber!!,
                100,
                currentTimestamp.toString(),
            )
            saveRecordUseCase.execute(record)

        }


    fun addMeetingRoom() = isAddMeetingRoomClick.postValue(true)

    fun bookAnAppointment() = isBookAnAppointmentClick.postValue(true)

    fun getAllRecords() = viewModelScope.launch {
        getAllRecordsUseCase.execute().collect {

            dataRecordList.postValue(it)
        }
    }


    private fun getMeetingRoomFromFirebase() {
        getMeetingRoomFromFirebaseUseCase.execute()
    }

    init {
        getMeetingRoomFromFirebase()
    }


}