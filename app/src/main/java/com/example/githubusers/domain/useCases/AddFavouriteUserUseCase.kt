package com.example.githubusers.domain.useCases

import com.example.githubusers.domain.models.UserInfo
import com.example.githubusers.domain.Repository
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class AddFavouriteUserUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(userInfo: UserInfo): Disposable {
        return repository.addFavouriteUser(userInfo)
    }

}