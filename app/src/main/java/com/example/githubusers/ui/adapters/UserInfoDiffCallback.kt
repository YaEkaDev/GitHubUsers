package com.example.githubusers.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.githubusers.data.models.UserShortInfo

class UserInfoDiffCallback: DiffUtil.ItemCallback<UserShortInfo>() {

    override fun areItemsTheSame(oldItem: UserShortInfo, newItem: UserShortInfo): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: UserShortInfo, newItem: UserShortInfo): Boolean {
        return oldItem.equals(newItem)
    }
}