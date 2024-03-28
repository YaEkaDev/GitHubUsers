package com.example.githubusers.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.githubusers.R
import com.example.githubusers.databinding.ItemUserInfoBinding
import com.example.githubusers.data.models.UserShortInfo
import com.squareup.picasso.Picasso

class UserShortInfoAdapter(private val context: Context): ListAdapter<UserShortInfo, UserInfoViewHolder>(UserInfoDiffCallback()) {

    var onReachEndListener: OnReachEndListener? = null
    var onUserClickListener: OnUserClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoViewHolder {
        val binding = ItemUserInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return UserInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) {
        val user = getItem(position)
        with(holder){
            binding.tvLogin.text = user.login
            binding.tvID.text = String.format(context.resources.getString(R.string.id_template), user.id.toString())
            Picasso.get().load(user.avatarUrl).into(binding.ivAvatar)

            if (position >= currentList.size - 10 && onReachEndListener != null) {
                onReachEndListener?.onReachEnd()
            }
            itemView.setOnClickListener { onUserClickListener?.onUserClick(user) }
        }
    }
    interface OnReachEndListener {
        fun onReachEnd()
    }

    interface OnUserClickListener{
        fun onUserClick(userShortInfo: UserShortInfo)
    }


}