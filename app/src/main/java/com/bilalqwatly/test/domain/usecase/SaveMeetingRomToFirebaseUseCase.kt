package com.bilalqwatly.test.domain.usecase

import com.bilalqwatly.test.data.model.MeetingRoomModel
import com.bilalqwatly.test.data.model.StateModel
import com.bilalqwatly.test.domain.repository.AppRepository


class SaveMeetingRomToFirebaseUseCase(private val appRepository: AppRepository) {
    fun execute(meetingRoomModel: MeetingRoomModel) {
        appRepository.saveMeetingRoomToFirebase(meetingRoomModel)
    }
}
