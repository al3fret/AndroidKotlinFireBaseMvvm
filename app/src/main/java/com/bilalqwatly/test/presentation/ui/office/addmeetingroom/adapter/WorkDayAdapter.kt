package com.bilalqwatly.test.presentation.ui.office.addmeetingroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bilalqwatly.test.R
import com.bilalqwatly.test.data.model.PeriodModel
import com.bilalqwatly.test.data.model.WorkDayModel
import com.bilalqwatly.test.databinding.ListItemWorkDayBinding

import com.bilalqwatly.test.presentation.ui.base.adapter.BaseListAdapter
import com.bilalqwatly.test.presentation.ui.base.adapter.BaseViewHolder
import com.bilalqwatly.test.presentation.ui.callback.OnAddPeriodClickListener
import com.bilalqwatly.test.presentation.ui.callback.OnItemClickListener
import com.bilalqwatly.test.presentation.ui.callback.OnSetTimePeriodClickListener


class WorkDayAdapter(
    context: Context,
    private val onRemoveWorkDayItemClickListener: OnItemClickListener<WorkDayModel>,
    private val onRemovePeriodClickListener: OnItemClickListener<PeriodModel>,
    private val onAddPeriodClickListener: OnAddPeriodClickListener,
    private val onSetDateClickListener: OnItemClickListener<WorkDayModel>,
    private val onSetFromPeriodClickListener: OnSetTimePeriodClickListener,
    private val onSetToPeriodClickListener: OnSetTimePeriodClickListener,
) :
    BaseListAdapter<WorkDayModel, BaseViewHolder<WorkDayModel, *>>(
        context,
        onRemoveWorkDayItemClickListener,
    ) {

    internal inner class WorkDayViewHolder(itemView: ListItemWorkDayBinding) :
        BaseViewHolder<WorkDayModel, ListItemWorkDayBinding>(itemView) {
        override fun onBind(item: WorkDayModel, position: Int) {
            binding.data = item
            binding.ivRemove.setOnClickListener {
                onRemoveWorkDayItemClickListener.onClick(
                    item,
                    position
                )
            }
            binding.btnSetDate.setOnClickListener {
                onSetDateClickListener.onClick(
                    item,
                    position
                )
            }
            binding.fabWorksDays.setOnClickListener {
                onAddPeriodClickListener.onClickAddPeriod(position)
            }
            val adapter = PeriodAdapter(
                context, onRemovePeriodClickListener, onSetFromPeriodClickListener,
                onSetToPeriodClickListener,
                position
            )
            binding.rvList.adapter = adapter
            binding.data = item

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<WorkDayModel, *> {
        val binding: ListItemWorkDayBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.list_item_work_day,
            parent,
            false
        )
        return WorkDayViewHolder(
            binding
        )
    }
}