package com.example.githubusers.presentation.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubusers.data.RepositoryImpl
import com.example.githubusers.domain.models.UserInfo
import com.example.githubusers.domain.useCases.AddFavouriteUserUseCase
import com.example.githubusers.domain.useCases.GetFavouriteUserByIdUseCase
import com.example.githubusers.domain.useCases.RemoveFavouriteUserUseCase
import io.reactivex.disposables.CompositeDisposable

class UserInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    private val repository = RepositoryImpl(application)

    private val getFavouriteUserByIdUseCase = GetFavouriteUserByIdUseCase(repository)
    private val addFavouriteUserUseCase = AddFavouriteUserUseCase(repository)
    private val removeFavouriteUserUseCase = RemoveFavouriteUserUseCase(repository)


    fun getFavouriteUser(id: Int): LiveData<UserInfo>{
        return getFavouriteUserByIdUseCase(id)
    }

    fun insertFavouriteUser(userInfo: UserInfo) {
        val disposable = addFavouriteUserUseCase(userInfo)
        compositeDisposable.add(disposable)
    }

    fun removeFavouriteUser(id: Int) {
        val disposable = removeFavouriteUserUseCase(id)
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}