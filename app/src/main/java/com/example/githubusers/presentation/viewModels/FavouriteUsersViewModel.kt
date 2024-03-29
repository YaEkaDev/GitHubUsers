package com.example.githubusers.presentation.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubusers.data.RepositoryImpl
import com.example.githubusers.domain.models.UserInfo
import com.example.githubusers.domain.useCases.GetFavouriteUsersUseCase

class FavouriteUsersViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)
    private val getFavouriteUsersUseCase = GetFavouriteUsersUseCase(repository)

    fun getFavouriteUsers(): LiveData<List<UserInfo>> {
        return getFavouriteUsersUseCase()
    }
}