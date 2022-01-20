package com.bilalqwatly.test.data.repository.datasource

import com.bilalqwatly.test.data.model.AppointmentModel
import com.bilalqwatly.test.data.model.MeetingRoomModel
import com.bilalqwatly.test.data.model.StateModel
import kotlinx.coroutines.flow.Flow


// Interface defining the methods to be performed in the remote database, i.e, firebase..
interface RemoteDataSource {
    fun saveMeetingRoomToFirebase(meetingRoomModel: MeetingRoomModel)
    fun bookAppointmentToFirebase(appointmentModel: AppointmentModel)
    fun fetchMeetingRoomsFromFirebase()
}
