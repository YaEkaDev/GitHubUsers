package com.example.githubusers.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.R
import com.example.githubusers.databinding.ItemUserInfoBinding
import com.example.githubusers.data.models.UserInfo
import com.squareup.picasso.Picasso

class UserInfoAdapter(private val context: Context): RecyclerView.Adapter<UserInfoViewHolder>()  {

    var onUserClickListener: OnUserClickListener? = null
    var favouriteUsersList = listOf<UserInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoViewHolder {
        val binding = ItemUserInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return UserInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) {
        val user = favouriteUsersList.get(position)
        with(holder){
            binding.tvLogin.text = user.login
            binding.tvID.text = String.format(context.resources.getString(R.string.id_template), user.id.toString())
            Picasso.get().load(user.avatarUrl).into(binding.ivAvatar)

            itemView.setOnClickListener { onUserClickListener?.onUserClick(user) }
        }
    }

    override fun getItemCount(): Int {
        return favouriteUsersList.size
    }
    interface OnUserClickListener{
        fun onUserClick(userInfo: UserInfo)
    }
}