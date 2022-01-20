package com.bilalqwatly.test.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bilalqwatly.test.data.model.RecordModel
import kotlinx.coroutines.flow.Flow

// DAO Interface defines all the methods which queries SQL statements into the database,
// and the resultant data is returned as required.
@Dao
interface LootahDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recordModel: RecordModel)



    @Query("SELECT * FROM records ORDER BY record_timestamp DESC")
    fun getAllRecords(): Flow<List<RecordModel>>

    @Query("SELECT * FROM records WHERE record_receiver_contact_number = :tPhoneNumber ORDER BY record_timestamp DESC")
    fun getAllRecordsOfParticularContact(tPhoneNumber: String): Flow<List<RecordModel>>


}
