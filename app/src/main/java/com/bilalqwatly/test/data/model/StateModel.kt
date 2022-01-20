package com.bilalqwatly.test.data.model

// Model class which holds all the fields for the remote database(Firebase) based on status of the transactions.
data class StateModel(
    val id: String? = null,
    val phoneNumber: String? = null,
    val status: String? = null
)
