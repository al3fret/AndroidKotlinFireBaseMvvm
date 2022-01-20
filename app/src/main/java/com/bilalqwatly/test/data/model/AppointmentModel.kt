package com.bilalqwatly.test.data.model

import java.io.Serializable


data class AppointmentModel(
    val meetingRoom: String? = null,
    val workDay: String? = null,
    val period: String? = null,
    val userMobileNumber: String? = null,
): Serializable
