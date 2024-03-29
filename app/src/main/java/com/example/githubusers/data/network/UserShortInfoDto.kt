package com.example.githubusers.data.network

import com.google.gson.annotations.SerializedName

data class UserShortInfoDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("url")
    val url: String
)
