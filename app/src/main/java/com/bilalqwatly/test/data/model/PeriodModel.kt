package com.bilalqwatly.test.data.model

import com.bilalqwatly.test.R
import java.io.Serializable


data class PeriodModel(

    var from: String? = "",
    var to: String? = "",
    val isAvailable: Boolean? = true
) : Serializable, Item() {
    override var isEnable: Boolean = isAvailable == true

    override val id: Int
        get() = 0
    override val content: String
        get() {

            return if (to.isNullOrEmpty()) {
                from!!
            } else {
                "$from - $to"
            }
        }


    override val size: Int
        get() = R.dimen._35sdp
}