package com.example.githubusers.domain.useCases

import com.example.githubusers.data.network.UserInfoDto
import com.example.githubusers.domain.Repository
import io.reactivex.Single

class GetUserDetailInfoUseCase(private val repository: Repository) {
    operator fun invoke(login: String): Single<UserInfoDto> {
        return repository.getUserDetailInfo(login)
    }
}