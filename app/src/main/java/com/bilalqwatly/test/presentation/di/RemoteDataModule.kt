package com.bilalqwatly.test.presentation.di

import com.bilalqwatly.test.data.preferences.SharedPreferencesManager
import com.bilalqwatly.test.data.repository.datasource.RemoteDataSource
import com.bilalqwatly.test.data.repository.datasourcelmpl.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// This Module class provide dependency injection for building the,
// RemoteDataSourceImpl(data->repository->DataSourceImpl) which is called from the Use Cases.
@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun providesRemoteDataSource(
        sharedPreferences: SharedPreferencesManager,
    ): RemoteDataSource {
        return RemoteDataSourceImpl(sharedPreferences)
    }

}
