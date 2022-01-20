package com.bilalqwatly.test.data.repository.datasource

import com.bilalqwatly.test.data.model.RecordModel
import kotlinx.coroutines.flow.Flow


// Interface defining the methods to be performed in the database.
interface LocalDataSource {
    suspend fun saveRecordToDB(recordModel: RecordModel)
    fun getAllRecords(): Flow<List<RecordModel>>
    fun getAllRecordsOfAParticularContact(phoneNumber: String): Flow<List<RecordModel>>

}

