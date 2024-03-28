package com.example.githubusers.ui.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubusers.data.database.AppDatabase
import com.example.githubusers.data.mappers.UserMapper
import com.example.githubusers.data.network.ApiFactory
import com.example.githubusers.data.models.UserInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val userInfoDao = AppDatabase.getInstance(application).userInfoDao()
    private val mapper = UserMapper()
    private val compositeDisposable = CompositeDisposable() //коллекция всех подписок

    fun getFavouriteUser(id: Int): LiveData<UserInfo> = MediatorLiveData<UserInfo>().apply {
        addSource(userInfoDao.getFavouriteUser(id)) {
            value = if (it == null) {
                it
            } else {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    fun insertFavouriteUser(user: UserInfo) {
        val disposable = userInfoDao.insertUser(mapper.mapEntityToDbModel(user))
            .subscribeOn(Schedulers.io())
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun removeFavouriteUser(id: Int) {
        val disposable = userInfoDao.remove(id)
            .subscribeOn(Schedulers.io())
            .subscribe()
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}