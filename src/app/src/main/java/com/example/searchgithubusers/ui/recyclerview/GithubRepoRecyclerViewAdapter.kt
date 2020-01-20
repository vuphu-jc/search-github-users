package com.example.searchgithubusers.ui.recyclerview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.example.searchgithubusers.R
import com.example.searchgithubusers.model.dto.GithubRepo
import com.example.searchgithubusers.model.dto.GithubUser
import com.example.searchgithubusers.ui.recyclerview.base.*


class GithubRepoRecyclerViewAdapter(context: Context, users: MutableList<CustomRecyclerView.Item>): CustomRecyclerView.Adapter(context, users){
    private val mFactory = CustomRecyclerView.ViewHolderFactory().apply {
        register(GithubRepo::class.java.name,
            GithubRepoViewHolder::class.java.name,
            R.layout.recyclerview_item_github_user)
        register(LoadingRecyclerViewItem::class.java.name,
            LoadingRecyclerViewViewHolder::class.java.name,
            R.layout.recyclerview_item_loading)
        register(
            TitleRecyclerViewItem::class.java.name,
            TitleRecyclerViewViewHolder::class.java.name,
            R.layout.recyclerview_item_title)
    }

    override fun getFactory(): CustomRecyclerView.ViewHolderFactory {
        return mFactory
    }

    fun showLoadingProgress(isShow: Boolean) {
        if (isShow) {
            items.add(LoadingRecyclerViewItem())
            notifyItemInserted(items.size)
        } else {
            items.removeAt(items.size - 1)
            notifyItemRemoved(items.size - 1)
        }
    }

    fun showError(message: String) {
        if (items.size == 0 || (items.size > 0 && items.last() !is TitleRecyclerViewItem)) {
            items.add(TitleRecyclerViewItem(message, true))
            notifyItemInserted(items.size)
        }
    }

    class GithubRepoViewHolder(context: Context, itemView: View): CustomRecyclerView.ViewHolder(context, itemView) {
        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        private val loginTextView: TextView = itemView.findViewById(R.id.loginTextView)
        private val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        private val layoutConstraintLayout: ViewGroup = itemView.findViewById(R.id.layoutConstraintLayout)

        override fun bind(data: Any?) {
            if (data is GithubRepo) {
                Glide.with(context)
                    .load(data.owner.avatarURL)
                    .into(avatarImageView)
                loginTextView.text = data.name
                idTextView.text = data.id.toString()

                layoutConstraintLayout.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(data.htmlURL)
                    context.startActivity(intent)
                }
            }
        }
    }
}