package com.example.githubusers.domain

import androidx.lifecycle.LiveData
import com.example.githubusers.domain.models.UserInfo
import com.example.githubusers.data.network.UserInfoDto
import com.example.githubusers.data.network.UserShortInfoDto
import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface Repository {
    fun getFavouriteUserById(id: Int): LiveData<UserInfo>

    fun getFavouriteUsers(): LiveData<List<UserInfo>>

    fun addFavouriteUser(userInfo: UserInfo): Disposable

    fun removeFavouriteUser(id: Int): Disposable

    fun getUserDetailInfo(login: String): Single<UserInfoDto>

    fun loadData(lastId: Int): Single<List<UserShortInfoDto>>

}