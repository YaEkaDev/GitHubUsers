package com.example.githubusers.domain.useCases

import androidx.lifecycle.LiveData
import com.example.githubusers.domain.models.UserInfo
import com.example.githubusers.domain.Repository

class GetFavouriteUsersUseCase(private val repository: Repository) {

    operator fun invoke(): LiveData<List<UserInfo>> {
        return repository.getFavouriteUsers()
    }

}