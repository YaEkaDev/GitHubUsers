package com.example.githubusers.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_users")
data class UserInfoDbModel(
    @PrimaryKey
    val id: Int,
    val login: String,
    val name: String? = null,
    val email: String? = null,
    val company: String? = null,
    val avatarUrl: String,
    val followers_count: Int,
    val following_count: Int,
    val dateCreating: String
)
