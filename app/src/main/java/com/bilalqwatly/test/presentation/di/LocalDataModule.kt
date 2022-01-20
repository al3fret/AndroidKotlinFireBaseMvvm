package com.bilalqwatly.test.presentation.di


import com.bilalqwatly.test.data.database.LootahDAO
import com.bilalqwatly.test.data.repository.datasource.LocalDataSource
import com.bilalqwatly.test.data.repository.datasourcelmpl.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// This Module class provide dependency injection for building the,
// RecordLocalDataSourceImpl(data->repository->DataSourceImpl) which is called from the Use Cases.
@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(lootahDAO: LootahDAO): LocalDataSource {
        return LocalDataSourceImpl(lootahDAO)
    }

}
