package com.bilalqwatly.test.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bilalqwatly.test.data.model.RecordModel

// Database class which holds all the entity classes, and build the database object.
@Database(
    entities = [RecordModel::class],
    version = 1,
    exportSchema = false
)
abstract class LootahDatabase : RoomDatabase() {
    abstract fun getRecordDao(): LootahDAO
}
