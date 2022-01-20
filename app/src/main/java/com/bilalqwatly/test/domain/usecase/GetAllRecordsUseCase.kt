package com.bilalqwatly.test.domain.usecase


import com.bilalqwatly.test.data.model.RecordModel
import com.bilalqwatly.test.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

// Use case for fetching all the records in the database.
class GetAllRecordsUseCase(private val appRepository: AppRepository) {
    fun execute(): Flow<List<RecordModel>> {
        return appRepository.getAllRecords()
    }
}
