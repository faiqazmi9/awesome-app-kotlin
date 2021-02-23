package com.example.awesomeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.awesomeapp.R
import com.example.awesomeapp.model.Item

class ItemAdapter(private val mItems: List<*>, private val mLayoutManager: GridLayoutManager) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private var itemOnClickListener: ItemOnClickListener? = null

    override fun getItemViewType(position: Int): Int {
        val spanCount = mLayoutManager.spanCount
        return if (spanCount == SPAN_COUNT_ONE) {
            VIEW_TYPE_BIG
        } else {
            VIEW_TYPE_SMALL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View
        if (viewType == VIEW_TYPE_BIG) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        }
        return ItemViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Item = mItems[position] as Item
        holder.title.setText(item.title)
        holder.iv.setImageResource(item.imgResId)
        holder.mView.setOnClickListener { view: View? ->
            if (itemOnClickListener != null) {
                itemOnClickListener!!.onClick(position, item)
            }
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class ItemViewHolder internal constructor(itemView: View, viewType: Int) :
        RecyclerView.ViewHolder(itemView) {
        var iv: ImageView
        var title: TextView
        var mView: ConstraintLayout

        init {
            mView = itemView.findViewById<View>(R.id.view) as ConstraintLayout
            iv = itemView.findViewById<View>(R.id.image) as ImageView
            title = itemView.findViewById<View>(R.id.title) as TextView
        }
    }

    fun itemOnClick(listener: ItemOnClickListener?) {
        itemOnClickListener = listener
    }

    interface ItemOnClickListener {
        fun onClick(pos: Int, item: Item?)
    }

    companion object {
        const val SPAN_COUNT_ONE = 1
        const val SPAN_COUNT_TWO = 2
        private const val VIEW_TYPE_SMALL = 1
        private const val VIEW_TYPE_BIG = 2
    }
}