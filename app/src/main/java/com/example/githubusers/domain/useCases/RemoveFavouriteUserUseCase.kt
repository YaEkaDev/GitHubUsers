package com.example.githubusers.domain.useCases

import com.example.githubusers.domain.Repository
import io.reactivex.disposables.Disposable

class RemoveFavouriteUserUseCase(private val repository: Repository) {
    operator fun invoke(id: Int): Disposable{
        return repository.removeFavouriteUser(id)
    }
}