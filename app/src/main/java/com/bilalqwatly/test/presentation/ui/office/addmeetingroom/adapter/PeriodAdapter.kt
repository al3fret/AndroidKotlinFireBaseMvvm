package com.bilalqwatly.test.presentation.ui.office.addmeetingroom.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bilalqwatly.test.R
import com.bilalqwatly.test.data.model.PeriodModel
import com.bilalqwatly.test.data.model.WorkDayModel
import com.bilalqwatly.test.databinding.ListItemPeriodBinding
import com.bilalqwatly.test.databinding.ListItemWorkDayBinding

import com.bilalqwatly.test.presentation.ui.base.adapter.BaseListAdapter
import com.bilalqwatly.test.presentation.ui.base.adapter.BaseViewHolder
import com.bilalqwatly.test.presentation.ui.callback.OnItemClickListener
import com.bilalqwatly.test.presentation.ui.callback.OnSetTimePeriodClickListener


class PeriodAdapter(
    context: Context,
    private val onRemoveItemClickListener: OnItemClickListener<PeriodModel>,
    private val onSetFromPeriodClickListener: OnSetTimePeriodClickListener,
    private val onSeToPeriodClickListener: OnSetTimePeriodClickListener,
    private val positionWorkDay: Int
) :
    BaseListAdapter<PeriodModel, BaseViewHolder<PeriodModel, *>>(
        context,
        onRemoveItemClickListener,
    ) {

    internal inner class PeriodViewHolder(itemView: ListItemPeriodBinding) :
        BaseViewHolder<PeriodModel, ListItemPeriodBinding>(itemView) {
        override fun onBind(item: PeriodModel, position: Int) {
            binding.data = item

            binding.ivRemove.setOnClickListener {
                onRemoveItemClickListener.onClick(
                    item,
                    position
                )
            }

            binding.btnSetFrom.setOnClickListener {
                onSetFromPeriodClickListener.onSetTime(
                    positionWorkDay,
                    position
                )
            }

            binding.btnSetTo.setOnClickListener {
                onSeToPeriodClickListener.onSetTime(
                    positionWorkDay,
                    position
                )
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<PeriodModel, *> {
        val binding: ListItemPeriodBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.list_item_period,
            parent,
            false
        )
        return PeriodViewHolder(
            binding
        )
    }
}