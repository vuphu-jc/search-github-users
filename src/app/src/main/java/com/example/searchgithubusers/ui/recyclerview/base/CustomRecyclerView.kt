package com.example.searchgithubusers.ui.recyclerview.base

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface CustomRecyclerView {
    interface Item {
    }

    abstract class ViewHolder(val context: Context, val itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        abstract fun bind(data: Any?)
    }

    class ViewHolderFactory {
        companion object {
            private const val ERROR_THROWN = "Error. Please insert mapping view type with class and resource"
        }

        val mMappingItem = HashMap<String, Int>()
        val mMappingViewHolder = HashMap<Int, Pair<String, Int>>()

        fun getViewType(itemClassName: String): Int {
            val mapping = mMappingItem[itemClassName]
            if (mapping == null) {
                throw Throwable(ERROR_THROWN)
            }
            return mapping
        }

        fun register(itemClassName: String, viewHolderClassName: String, layoutResource: Int) {
            val viewType = mMappingItem.size + 1
            mMappingItem[itemClassName] = viewType
            mMappingViewHolder[viewType] = Pair(viewHolderClassName, layoutResource)
        }

        fun createViewHolder(context: Context, parent: ViewGroup, viewType: Int): ViewHolder {
            val mapping = mMappingViewHolder[viewType]
            if (mapping == null) {
                throw Throwable(ERROR_THROWN)
            }
            val v = LayoutInflater.from(context).inflate(mapping.second, parent, false)
            return Class.forName(mapping.first).getConstructor(Context::class.java, View::class.java)
                .newInstance(context, v) as ViewHolder
        }
    }

    abstract class Adapter(val context: Context, val items: MutableList<Item>)
        : RecyclerView.Adapter<ViewHolder>() {

        abstract fun getFactory(): ViewHolderFactory

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return getFactory().createViewHolder(context, parent, viewType)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun getItemViewType(position: Int): Int {
            return getFactory().getViewType(items[position]::class.java.name)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position])
        }
    }
}