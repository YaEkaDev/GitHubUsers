package com.example.githubusers.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getListUsers(
        @Query(QUERY_PARAM_LIMIT) limit: Int = 30,
        @Query(QUERY_PARAM_LAST_ID) lastID: Int
    ): Single<List<UserShortInfoDto>>

    @GET("users/{$PATH_PARAM_USERNAME}")
    fun getUserInfo(
        @Path(PATH_PARAM_USERNAME) login: String
    ): Single<UserInfoDto>

    companion object {
        private const val QUERY_PARAM_LIMIT = "per_page"
        private const val QUERY_PARAM_LAST_ID = "since"
        private const val PATH_PARAM_USERNAME = "username"
    }

}