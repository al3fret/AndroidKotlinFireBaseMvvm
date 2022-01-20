package com.bilalqwatly.test.domain.repository

import com.bilalqwatly.test.data.model.AppointmentModel
import com.bilalqwatly.test.data.model.MeetingRoomModel

import kotlinx.coroutines.flow.Flow

// Interface which defines all the use-cases of the project.
interface AppRepository {

    fun saveMeetingRoomToFirebase(meetingRoomModel: MeetingRoomModel)
    fun bookAppointmentToFirebase(appointmentModel: AppointmentModel)
    fun getMeetingRoomsFromFirebase()
}
