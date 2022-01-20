package com.bilalqwatly.test.domain.usecase

import com.bilalqwatly.test.data.model.RecordModel
import com.bilalqwatly.test.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow


class GetMeetingRoomFromFirebaseUseCase(private val appRepository: AppRepository) {
    fun execute(){
        return appRepository.getMeetingRoomsFromFirebase()
    }
}
