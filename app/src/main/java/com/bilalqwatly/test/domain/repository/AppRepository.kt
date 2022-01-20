package com.bilalqwatly.test.domain.repository

import androidx.lifecycle.LiveData
import com.bilalqwatly.test.data.model.AppointmentModel
import com.bilalqwatly.test.data.model.MeetingRoomModel
import com.bilalqwatly.test.data.model.RecordModel
import com.bilalqwatly.test.data.model.StateModel

import kotlinx.coroutines.flow.Flow

// Interface which defines all the use-cases of the project.
interface AppRepository {
    suspend fun saveRecord(record: RecordModel)
    fun getAllRecords(): Flow<List<RecordModel>>
    fun getAllRecordsOfAParticularContact(phoneNumber: String): Flow<List<RecordModel>>
    fun saveMeetingRoomToFirebase(meetingRoomModel: MeetingRoomModel)
    fun bookAppointmentToFirebase(appointmentModel: AppointmentModel)
    fun getMeetingRoomsFromFirebase()
}
