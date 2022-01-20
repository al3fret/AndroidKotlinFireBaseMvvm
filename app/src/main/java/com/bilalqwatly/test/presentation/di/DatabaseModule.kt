package com.bilalqwatly.test.presentation.di

import android.app.Application
import androidx.room.Room
import com.bilalqwatly.test.data.database.LootahDAO
import com.bilalqwatly.test.data.database.LootahDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Database module creates the object for the Record Database and the Record DAO.
// Hilt can automatically inject it into fields or constructor arguments where we want to use it.
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesNewsDatabase(app: Application): LootahDatabase {
        return Room.databaseBuilder(app, LootahDatabase::class.java, "record_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(recordDatabase: LootahDatabase): LootahDAO {
        return recordDatabase.getRecordDao()
    }

}
