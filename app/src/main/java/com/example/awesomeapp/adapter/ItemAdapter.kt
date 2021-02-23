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
    private var itemOnClickListener: ItemListener? = null

    override fun getItemViewType(position: Int): Int {
        val spanCount = mLayoutManager.spanCount
        return if (spanCount == SPAN_COUNT_ONE) {
            VIEW_TYPE_BIG
        } else {
            VIEW_TYPE_SMALL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View = if (viewType == VIEW_TYPE_BIG) {
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        }
        return ItemViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Item = mItems[position] as Item
        holder.title.text = item.title
        holder.iv.setImageResource(item.imgResId)
        holder.mView.setOnClickListener {
            itemOnClickListener?.onClick(position, item)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class ItemViewHolder internal constructor(itemView: View, viewType: Int) :
        RecyclerView.ViewHolder(itemView) {
        var iv: ImageView = itemView.findViewById<View>(R.id.image) as ImageView
        var title: TextView = itemView.findViewById<View>(R.id.title) as TextView
        var mView: ConstraintLayout = itemView.findViewById<View>(R.id.view) as ConstraintLayout
    }

    fun itemClick(listener: ItemListener) {
        this.itemOnClickListener = listener
    }

    public interface ItemListener {
        fun onClick(position: Int, item: Item)
    }

    companion object {
        const val SPAN_COUNT_ONE = 1
        const val SPAN_COUNT_TWO = 2
        private const val VIEW_TYPE_SMALL = 1
        private const val VIEW_TYPE_BIG = 2
    }
}