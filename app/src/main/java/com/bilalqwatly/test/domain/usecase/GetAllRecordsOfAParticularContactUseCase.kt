package com.bilalqwatly.test.domain.usecase

import com.bilalqwatly.test.data.model.RecordModel
import com.bilalqwatly.test.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

// Use case for fetching all the records in the database defined of a particular contact.
class GetAllRecordsOfAParticularContactUseCase(private val appRepository: AppRepository) {
    fun execute(phoneNumber: String): Flow<List<RecordModel>> {
        return appRepository.getAllRecordsOfAParticularContact(phoneNumber)
    }
}
