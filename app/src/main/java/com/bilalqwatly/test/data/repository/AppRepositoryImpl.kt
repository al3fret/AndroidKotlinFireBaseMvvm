package com.bilalqwatly.test.data.repository


import com.bilalqwatly.test.data.model.AppointmentModel
import com.bilalqwatly.test.data.model.MeetingRoomModel
import com.bilalqwatly.test.data.repository.datasource.RemoteDataSource
import com.bilalqwatly.test.domain.repository.AppRepository

// Class defining the functionality to the methods described in the [AppRepository.kt] interface in the Domain section.
class AppRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : AppRepository {

    override fun saveMeetingRoomToFirebase(meetingRoomModel: MeetingRoomModel) {
        remoteDataSource.saveMeetingRoomToFirebase(meetingRoomModel)
    }

    override fun bookAppointmentToFirebase(appointmentModel: AppointmentModel) {
        remoteDataSource.bookAppointmentToFirebase(appointmentModel)
    }

    override fun getMeetingRoomsFromFirebase() {
        remoteDataSource.fetchMeetingRoomsFromFirebase()
    }
}
