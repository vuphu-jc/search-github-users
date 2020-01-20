package com.example.searchgithubusers.ui.recyclerview.base

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.searchgithubusers.R

class TitleRecyclerViewItem(val title: String, var error: Boolean = false): CustomRecyclerView.Item {
}

class TitleRecyclerViewViewHolder(context: Context, itemView: View): CustomRecyclerView.ViewHolder(context, itemView) {
    val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)

    override fun bind(data: Any?) {
        if (data is TitleRecyclerViewItem) {
            titleTextView.text = data.title
            if (data.error)
                titleTextView.setTextColor(context.getColor(android.R.color.holo_red_light))
            else
                titleTextView.setTextColor(context.getColor(android.R.color.black))
        }
    }
}
