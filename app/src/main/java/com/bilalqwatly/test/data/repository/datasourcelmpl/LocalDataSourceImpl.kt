package com.bilalqwatly.test.data.repository.datasourcelmpl

import com.bilalqwatly.test.data.database.LootahDAO
import com.bilalqwatly.test.data.model.RecordModel
import com.bilalqwatly.test.data.repository.datasource.LocalDataSource
import kotlinx.coroutines.flow.Flow


// Class defining the functionality to the methods described in the [RecordLocalDataSource.kt] interface
class LocalDataSourceImpl(
    private val recordDAO: LootahDAO
) : LocalDataSource {

    override suspend fun saveRecordToDB(record: RecordModel) {
        recordDAO.insert(record)
    }

    override fun getAllRecords(): Flow<List<RecordModel>> {
        return recordDAO.getAllRecords()
    }

    override fun getAllRecordsOfAParticularContact(phoneNumber: String): Flow<List<RecordModel>> {
        return recordDAO.getAllRecordsOfParticularContact(phoneNumber)
    }


}
