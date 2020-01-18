package com.example.searchgithubusers.ui.recyclerview

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import com.bumptech.glide.Glide
import com.example.searchgithubusers.R
import com.example.searchgithubusers.model.dto.GithubUser
import com.example.searchgithubusers.ui.activity.github_user_detail.GithubUserDetailActivity
import com.example.searchgithubusers.ui.recyclerview.base.CustomRecyclerView
import com.example.searchgithubusers.ui.recyclerview.base.LoadingRecyclerViewItem
import com.example.searchgithubusers.ui.recyclerview.base.LoadingRecyclerViewViewHolder

class GithubUserRecyclerViewAdapter(context: Context, users: MutableList<CustomRecyclerView.Item>): CustomRecyclerView.Adapter(context, users){
    private val mFactory = CustomRecyclerView.ViewHolderFactory().apply {
        register(GithubUser::class.java.name,
            GithubUserViewHolder::class.java.name,
            R.layout.recyclerview_item_github_user)
        register(LoadingRecyclerViewItem::class.java.name,
            LoadingRecyclerViewViewHolder::class.java.name,
            R.layout.recyclerview_item_loading)
    }

    private var mIsLoading: Boolean = false

    override fun getFactory(): CustomRecyclerView.ViewHolderFactory {
        return mFactory
    }

    fun isLoading(): Boolean {
        return mIsLoading
    }

    fun showLoadingProgress(isShow: Boolean) {
        if (isShow) {
            items.add(LoadingRecyclerViewItem())
            notifyItemInserted(items.size)
        } else {
            items.removeAt(items.size - 1)
            notifyItemRemoved(items.size - 1)
        }
        mIsLoading = isShow
    }

    class GithubUserViewHolder(context: Context, itemView: View): CustomRecyclerView.ViewHolder(context, itemView) {
        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        private val loginTextView: TextView = itemView.findViewById(R.id.loginTextView)
        private val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        private val layoutConstraintLayout: ViewGroup = itemView.findViewById(R.id.layoutConstraintLayout)

        override fun bind(data: Any?) {
            if (data is GithubUser) {
                Glide.with(context)
                    .load(data.avatarURL)
                    .into(avatarImageView)
                loginTextView.text = data.login
                idTextView.text = data.id.toString()

                layoutConstraintLayout.setOnClickListener {
                    val intent = Intent(context, GithubUserDetailActivity::class.java)
                    intent.putExtra(GithubUserDetailActivity.GITHUB_USER_EXTRA, data)
                    context.startActivity(intent)
                }
            }
        }
    }
}