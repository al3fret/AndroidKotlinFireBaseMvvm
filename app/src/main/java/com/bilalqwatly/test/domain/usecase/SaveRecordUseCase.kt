package com.bilalqwatly.test.domain.usecase

import com.bilalqwatly.test.data.model.RecordModel
import com.bilalqwatly.test.domain.repository.AppRepository


// Use Case To Save a New Record in the database.
class SaveRecordUseCase(private val appRepository: AppRepository) {
    suspend fun execute(record: RecordModel): Unit {
        appRepository.saveRecord(record)
    }
}
