package com.bilalqwatly.test.data.model

import java.io.Serializable


abstract class Item : Serializable {
    abstract var isEnable: Boolean
    abstract val id: Int?
    abstract val content: String?
    abstract val size: Int?
}
