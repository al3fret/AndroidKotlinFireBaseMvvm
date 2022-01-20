package com.bilalqwatly.test.data.repository


import com.bilalqwatly.test.data.model.AppointmentModel
import com.bilalqwatly.test.data.model.MeetingRoomModel
import com.bilalqwatly.test.data.model.RecordModel
import com.bilalqwatly.test.data.repository.datasource.LocalDataSource
import com.bilalqwatly.test.data.repository.datasource.RemoteDataSource
import com.bilalqwatly.test.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

// Class defining the functionality to the methods described in the [AppRepository.kt] interface in the Domain section.
class AppRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : AppRepository {
    override suspend fun saveRecord(record: RecordModel) {
        localDataSource.saveRecordToDB(record)
    }

    override fun getAllRecords(): Flow<List<RecordModel>> {
        return localDataSource.getAllRecords()
    }

    override fun getAllRecordsOfAParticularContact(phoneNumber: String): Flow<List<RecordModel>> {
        return localDataSource.getAllRecordsOfAParticularContact(phoneNumber)
    }

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
