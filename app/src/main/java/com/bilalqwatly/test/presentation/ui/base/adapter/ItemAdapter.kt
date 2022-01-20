package com.bilalqwatly.test.presentation.ui.base.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.bilalqwatly.test.R
import com.bilalqwatly.test.data.model.Item
import com.bilalqwatly.test.databinding.ItemAdapterBinding
import java.io.Serializable
import java.util.ArrayList


class ItemAdapter : ArrayAdapter<Item> {
    private var mInflater: LayoutInflater
    private var data: MutableList<Item>?
    private var style: Int

    constructor(context: Context, items: MutableList<Item>?, style: Int) : super(
        context,
        0,
        items!!
    ) {
        mInflater = LayoutInflater.from(context)
        data = items
        this.style = style
    }

    constructor(context: Context, style: Int) : super(context, 0) {
        mInflater = LayoutInflater.from(context)
        this.style = style
        data = ArrayList<Item>()
    }


    fun insertItemInIndex(item: Item, index: Int) {
        if (index < 0) return
        if (index < data!!.size) {
            data!!.add(index, item)
        } else data!!.add(item)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return data!!.size
    }

    val limitItems: Int
        get() = data!!.size.coerceAtMost(6)


    // Clear previous data, then add the new data
    fun submitData(data: List<Item>?) {
        if (data == null) return
        if (this.data == null) this.data = ArrayList()
        val size = this.data!!.size
        this.data!!.clear()
        this.data!!.addAll(data)
        if (this.data!!.size > size) {
            val itemCount = this.data!!.size - size
            //   notifyItemRangeInserted(size, itemCount)
            //    notifyItemRangeChanged(size, itemCount)

            notifyDataSetChanged()
        } else {
            //   notifyItemRangeChanged(0, size)

            notifyDataSetChanged()
        }

    }


    fun clearData() {
        if (data != null) {
            for (i in data!!.indices) {
                removeItem(data!![i])
            }
            notifyDataSetChanged()
        }
    }

    fun removeItem(item: Item?) {
        if (item == null || data == null) return
        val index = data!!.indexOf(item)
        if (index == -1) return  // not found
        data!!.removeAt(index)
    }

    override fun getItem(position: Int): Item {

        return data!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun isEnabled(position: Int): Boolean {
        return getItem(position).isEnable
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemAdapterBinding.inflate(mInflater)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.tvContent.setTextAppearance(style)
        }
        binding.data = getItem(position)
        if (position != 0) {
            if (!getItem(position).isEnable) {
                binding.tvContent.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.color_ACACAC
                    )
                )
                binding.tvContent.setCompoundDrawablesWithIntrinsicBounds(
                    null, null, ContextCompat.getDrawable(
                        context, R.drawable.ic_checked_spinner
                    ), null
                )
            } else {
                binding.tvContent.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.color_FFFFFF
                    )
                )
                binding.tvContent.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }
        }
        return binding.root
    }
}