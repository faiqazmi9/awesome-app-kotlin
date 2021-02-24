package com.example.awesomeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.awesomeapp.R
import com.example.awesomeapp.model.Item
import org.apache.commons.lang3.StringUtils
import timber.log.Timber

class ItemAdapter(
    private val items: List<Item>,
    private val mContext: Context,
    private val mLayoutManager: GridLayoutManager
) :
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
        val view: View = if (viewType == VIEW_TYPE_BIG) {
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        }
        return ItemViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val response = items[position]
        if (StringUtils.isNotEmpty(items[position].tiny)) {
            try {
                Glide.with(mContext).load(
                    items[position].original
                )
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.iv)
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        } else {
            try {
                Glide.with(mContext).load(R.drawable.ic_launcher_foreground)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.iv)
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
        holder.title.text = response.photographer
        holder.mView.setOnClickListener {
            if (itemOnClickListener != null) {
                itemOnClickListener!!.onClick(position, response)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder internal constructor(itemView: View, viewType: Int) :
        RecyclerView.ViewHolder(itemView) {
        var iv: ImageView = itemView.findViewById<View>(R.id.image) as ImageView
        var title: TextView = itemView.findViewById<View>(R.id.title) as TextView
        var mView: ConstraintLayout = itemView.findViewById<View>(R.id.view) as ConstraintLayout

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