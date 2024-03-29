package com.example.githubusers.domain.models

import java.io.Serializable

data class UserInfo(
    val id: Int,
    val login: String,
    val name: String? = null,
    val email: String? = null,
    val company: String? = null,
    val avatarUrl: String,
    val followers_count: Int,
    val following_count: Int,
    val dateCreating: String,
) : Serializable
