package com.example.githubusers.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.githubusers.data.database.AppDatabase
import com.example.githubusers.data.mappers.UserMapper
import com.example.githubusers.data.models.UserInfo

class FavouriteUsersViewModel(application: Application) : AndroidViewModel(application) {

    private val userInfoDao = AppDatabase.getInstance(application).userInfoDao()
    private val mapper = UserMapper()

    fun getFavouriteUsers(): LiveData<List<UserInfo>> = MediatorLiveData<List<UserInfo>>().apply {
        addSource(userInfoDao.getAllFavouriteUsers()) {
            value = if (it == null) {
                it
            } else {
                mapper.mapListDbModelToListEntity(it)
            }
        }
    }
}