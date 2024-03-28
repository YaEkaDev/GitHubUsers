package com.example.githubusers.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserShortInfo(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val url: String,
) : Serializable