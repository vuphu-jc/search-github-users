package com.example.searchgithubusers.ui.recyclerview.base

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.searchgithubusers.R

class LoadingRecyclerViewItem(): CustomRecyclerView.Item {
}

class LoadingRecyclerViewViewHolder(context: Context, itemView: View): CustomRecyclerView.ViewHolder(context, itemView) {
    val loadingImageView: ImageView = itemView.findViewById(R.id.loadingImageView)

    override fun bind(data: Any?) {
        Glide.with(context)
            .load(R.drawable.ic_loading)
            .into(loadingImageView)
    }
}
