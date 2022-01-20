package com.bilalqwatly.test.domain.usecase

import com.bilalqwatly.test.domain.repository.AppRepository


class GetMeetingRoomFromFirebaseUseCase(private val appRepository: AppRepository) {
    fun execute(){
        return appRepository.getMeetingRoomsFromFirebase()
    }
}
