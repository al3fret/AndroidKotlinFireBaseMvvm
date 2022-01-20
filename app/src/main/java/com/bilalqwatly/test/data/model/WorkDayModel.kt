package com.bilalqwatly.test.data.model

import com.bilalqwatly.test.R
import java.io.Serializable



data class WorkDayModel(
    var date: String? = "",
    var periodModels: List<PeriodModel>? = null,
) : Serializable ,Item() {
    override var isEnable: Boolean = true

    override val id: Int
        get() = 0
    override val content: String?
        get() = date
    override val size: Int
        get() = R.dimen._35sdp
}