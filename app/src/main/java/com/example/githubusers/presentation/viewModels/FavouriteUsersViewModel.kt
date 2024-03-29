package com.example.githubusers.presentation.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.domain.models.UserInfo
import com.example.githubusers.domain.useCases.GetFavouriteUsersUseCase
import javax.inject.Inject

class FavouriteUsersViewModel @Inject constructor(
    private val getFavouriteUsersUseCase: GetFavouriteUsersUseCase
) : ViewModel() {

    fun getFavouriteUsers(): LiveData<List<UserInfo>> {
        return getFavouriteUsersUseCase()
    }

}