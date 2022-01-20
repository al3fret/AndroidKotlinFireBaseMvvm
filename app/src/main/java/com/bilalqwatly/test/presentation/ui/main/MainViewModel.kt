package com.bilalqwatly.test.presentation.ui.main


import androidx.lifecycle.MutableLiveData
import com.bilalqwatly.test.data.preferences.SharedPreferencesManager
import com.bilalqwatly.test.domain.usecase.GetMeetingRoomFromFirebaseUseCase
import com.bilalqwatly.test.presentation.ui.base.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    session: SharedPreferencesManager,
    private val getMeetingRoomFromFirebaseUseCase: GetMeetingRoomFromFirebaseUseCase,
) : BaseViewModel(session) {

    // create instance of firebase auth
    var auth: FirebaseAuth? = null
    var isAddMeetingRoomClick = MutableLiveData<Boolean>()
    var isBookAnAppointmentClick = MutableLiveData<Boolean>()
    var isLogoutClick = MutableLiveData<Boolean>()


    fun addMeetingRoom() = isAddMeetingRoomClick.postValue(true)

    fun bookAnAppointment() = isBookAnAppointmentClick.postValue(true)

    fun logout() {
        isLogoutClick.postValue(true)
    }

    private fun getMeetingRoomFromFirebase() {
        getMeetingRoomFromFirebaseUseCase.execute()
    }

    init {
        getMeetingRoomFromFirebase()
    }
}


