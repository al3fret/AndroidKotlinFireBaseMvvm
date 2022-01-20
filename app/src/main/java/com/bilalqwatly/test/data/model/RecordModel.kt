package com.bilalqwatly.test.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Model class which holds all the columnar fields for the local database.
@Entity(
    tableName = "records"
)
data class RecordModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "record_id")
    val id: Int,

    @ColumnInfo(name = "record_receiver_contact_number")
    val receiverContactNumber: String,

    @ColumnInfo(name = "record_amount_involved")
    val amountInvolved: Int,

    @ColumnInfo(name = "record_timestamp")
    val timestamp: String,


)
