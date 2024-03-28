package com.example.githubusers.data.network

import com.google.gson.annotations.SerializedName

data class UserInfoDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("followers")
    val followers_count: Int,
    @SerializedName("following")
    val following_count: Int,
    @SerializedName("created_at")
    val dateCreating: String
)
