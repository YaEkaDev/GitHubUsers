package com.example.githubusers.domain.useCases

import androidx.lifecycle.LiveData
import com.example.githubusers.domain.models.UserInfo
import com.example.githubusers.domain.Repository
import javax.inject.Inject

class GetFavouriteUserByIdUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(id: Int): LiveData<UserInfo>{
        return repository.getFavouriteUserById(id)
    }
}