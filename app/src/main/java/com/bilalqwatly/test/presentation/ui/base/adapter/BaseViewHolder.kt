package com.bilalqwatly.test.presentation.ui.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T, DB : ViewDataBinding>(var binding: DB) :
    RecyclerView.ViewHolder(
        binding.root
    ) {
    abstract fun onBind(item: T, position: Int)
}