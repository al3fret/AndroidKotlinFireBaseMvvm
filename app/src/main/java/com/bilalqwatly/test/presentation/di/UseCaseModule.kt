package com.bilalqwatly.test.presentation.di


import com.bilalqwatly.test.domain.repository.AppRepository
import com.bilalqwatly.test.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// UseCaseModule creates the dependency injection for the different Use Cases of the project defined in the Domain section.
@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun providesSaveRecordUseCase(appRepository: AppRepository): SaveRecordUseCase {
        return SaveRecordUseCase(appRepository)
    }

    @Singleton
    @Provides
    fun providesGetAllRecordsUseCase(appRepository: AppRepository): GetAllRecordsUseCase {
        return GetAllRecordsUseCase(appRepository)
    }

    @Singleton
    @Provides
    fun providesSaveMeetingRomToFirebaseUseCase(appRepository: AppRepository): SaveMeetingRomToFirebaseUseCase {
        return SaveMeetingRomToFirebaseUseCase(appRepository)
    }

    @Singleton
    @Provides
    fun providesBookAppointmentToFirebaseUseCase(appRepository: AppRepository): BookAppointmentToFirebaseUseCase {
        return BookAppointmentToFirebaseUseCase(appRepository)
    }

    @Singleton
    @Provides
    fun providesGetStateFromFirebaseUseCase(appRepository: AppRepository): GetMeetingRoomFromFirebaseUseCase {
        return GetMeetingRoomFromFirebaseUseCase(appRepository)
    }

    @Singleton
    @Provides
    fun providesGetAllRecordsOfAParticularContactUseCase(appRepository: AppRepository): GetAllRecordsOfAParticularContactUseCase {
        return GetAllRecordsOfAParticularContactUseCase(appRepository)
    }

}
