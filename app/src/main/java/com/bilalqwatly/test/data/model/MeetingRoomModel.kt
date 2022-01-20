package com.bilalqwatly.test.data.model

import com.bilalqwatly.test.R
import java.io.Serializable


data class MeetingRoomModel(
    val name: String? = "",
    val workDayModels: List<WorkDayModel>? = null,
) : Serializable, Item() {
    override var isEnable: Boolean = true

    override val id: Int
        get() = 0
    override val content: String?
        get() = name
    override val size: Int
        get() = R.dimen._35sdp
}