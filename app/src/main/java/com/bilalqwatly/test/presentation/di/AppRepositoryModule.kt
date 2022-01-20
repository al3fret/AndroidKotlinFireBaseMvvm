package com.bilalqwatly.test.presentation.di

import com.bilalqwatly.test.data.repository.AppRepositoryImpl
import com.bilalqwatly.test.data.repository.datasource.RemoteDataSource
import com.bilalqwatly.test.domain.repository.AppRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// This Module class provide dependency injection for building the,
// AppRepositoryImpl(data->repository) which is called from the Use Cases.
@InstallIn(SingletonComponent::class)
@Module
class AppRepositoryModule {

    @Singleton
    @Provides
    fun providesAppRepository(
        remoteDataSource: RemoteDataSource
    ): AppRepository {
        return AppRepositoryImpl( remoteDataSource)
    }

}
