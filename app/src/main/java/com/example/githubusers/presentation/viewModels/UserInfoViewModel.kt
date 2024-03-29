package com.example.githubusers.presentation.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.domain.models.UserInfo
import com.example.githubusers.domain.useCases.AddFavouriteUserUseCase
import com.example.githubusers.domain.useCases.GetFavouriteUserByIdUseCase
import com.example.githubusers.domain.useCases.RemoveFavouriteUserUseCase
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class UserInfoViewModel @Inject constructor(
    private val getFavouriteUserByIdUseCase: GetFavouriteUserByIdUseCase,
    private val addFavouriteUserUseCase: AddFavouriteUserUseCase,
    private val removeFavouriteUserUseCase: RemoveFavouriteUserUseCase,
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun getFavouriteUser(id: Int): LiveData<UserInfo> {
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