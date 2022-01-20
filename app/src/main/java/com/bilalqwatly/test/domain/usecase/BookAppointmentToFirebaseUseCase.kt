package com.bilalqwatly.test.domain.usecase

import com.bilalqwatly.test.data.model.AppointmentModel
import com.bilalqwatly.test.domain.repository.AppRepository


class BookAppointmentToFirebaseUseCase(private val appRepository: AppRepository) {
    fun execute(appointmentModel: AppointmentModel) {
        appRepository.bookAppointmentToFirebase(appointmentModel)
    }
}
