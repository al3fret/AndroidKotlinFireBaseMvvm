package com.bilalqwatly.test.presentation.ui.callback

interface OnItemClickListener<T> {
    fun onClick(item: T, position: Int)

}

interface OnAddPeriodClickListener {
    fun onClickAddPeriod(position: Int)
}

interface OnSetTimePeriodClickListener  {
    fun onSetTime(positionWorkDay: Int, positionPeriod: Int)
}