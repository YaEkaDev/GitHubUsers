package com.example.githubusers.domain.useCases

import com.example.githubusers.data.network.UserShortInfoDto
import com.example.githubusers.domain.Repository
import io.reactivex.Single

class LoadDataUseCase(private val repository: Repository) {
    operator fun invoke(lastId: Int): Single<List<UserShortInfoDto>>{
        return repository.loadData(lastId)
    }
}