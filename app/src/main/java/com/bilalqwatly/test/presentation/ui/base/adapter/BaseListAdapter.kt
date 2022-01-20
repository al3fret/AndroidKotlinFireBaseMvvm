package com.bilalqwatly.test.presentation.ui.base.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bilalqwatly.test.presentation.ui.callback.OnItemClickListener
import java.io.Serializable
import java.util.ArrayList


abstract class BaseListAdapter<T, VH : BaseViewHolder<T, *>> : RecyclerView.Adapter<VH> {
    var context: Context
    protected open lateinit var onItemClickListener: OnItemClickListener<T>
    protected lateinit var storageUrl: String
    private var data: MutableList<T?>? = null

    constructor(context: Context) {
        this.context = context
    }

    constructor(context: Context, onItemClickListener: OnItemClickListener<T>) {
        this.context = context
        this.onItemClickListener = onItemClickListener
    }

    constructor(
        context: Context,
        storageUrl: String,
        onItemClickListener: OnItemClickListener<T>
    ) {
        this.context = context
        this.storageUrl = storageUrl
        this.onItemClickListener = onItemClickListener
    }

    constructor(context: Context, storageUrl: String) {
        this.context = context
        this.storageUrl = storageUrl
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = if (data!!.size > position) data!![position] else null
        holder.onBind(item!!, position)
    }


    override fun getItemCount(): Int {

        if (data == null) return 0
        return data!!.size
    }

    fun getData(): MutableList<T?>? {
        return data
    }

    // Clear previous data, then add the new data
    open fun submitData(data: List<Serializable>?) {
        if (data == null) return
        if (this.data == null) this.data = ArrayList()
        val size = this.data!!.size
        this.data!!.clear()
        this.data!!.addAll(data as List<T>)
        if (this.data!!.size > size) {
            val itemCount = this.data!!.size - size
            notifyItemRangeInserted(size, itemCount)
            notifyItemRangeChanged(size, itemCount)
        } else {
            notifyItemRangeChanged(0, size)
        }
    }

    // Add the data to old data
    fun insertData(data: List<T>?) {
        if (data == null) return
        val insertIndex = this.data!!.size
        this.data!!.addAll(data)
        notifyItemRangeInserted(insertIndex, data.size)
    }

    fun insertItem(item: T) {
        data!!.add(item)
        notifyItemInserted(data!!.size - 1)
    }

    fun insertItemInIndex(item: T, index: Int) {
        if (index < 0) return
        if (index < data!!.size) {
            data!!.add(index, item)
        } else data!!.add(item)
        notifyDataSetChanged()
    }

    private fun removeItem(item: T?) {
        if (item == null) return
        val index = data!!.indexOf(item)
        if (index == -1) return  // not found
        data!!.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, data!!.size - 1)
    }

    fun isEmptyData(): Boolean {
        if (data == null) return false
        return data!!.isEmpty()
    }

    fun clearData() {
        for (i in data!!.indices) {
            removeItem(data!![i])
        }
    }


}







