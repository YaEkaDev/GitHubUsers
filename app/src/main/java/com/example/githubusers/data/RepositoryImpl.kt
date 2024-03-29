package com.example.githubusers.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.githubusers.data.database.AppDatabase
import com.example.githubusers.data.mappers.UserMapper
import com.example.githubusers.domain.models.UserInfo
import com.example.githubusers.data.network.ApiFactory
import com.example.githubusers.data.network.UserInfoDto
import com.example.githubusers.data.network.UserShortInfoDto
import com.example.githubusers.domain.Repository
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RepositoryImpl(application: Application) : Repository {

    private val mapper = UserMapper()
    private val userInfoDao = AppDatabase.getInstance(application).userInfoDao()

    override fun getFavouriteUserById(id: Int): LiveData<UserInfo> =
        MediatorLiveData<UserInfo>().apply {
            addSource(userInfoDao.getFavouriteUser(id)) {
                value = if (it == null) {
                    it
                } else {
                    mapper.mapDbModelToEntity(it)
                }
            }
        }

    override fun getFavouriteUsers(): LiveData<List<UserInfo>> =
        MediatorLiveData<List<UserInfo>>().apply {
            addSource(userInfoDao.getAllFavouriteUsers()) {
                value = if (it == null) {
                    it
                } else {
                    mapper.mapListDbModelToListEntity(it)
                }
            }
        }

    override fun addFavouriteUser(userInfo: UserInfo): Disposable {
        return userInfoDao.insertUser(mapper.mapEntityToDbModel(userInfo))
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun removeFavouriteUser(id: Int): Disposable {
        return userInfoDao.remove(id)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun getUserDetailInfo(login: String): Single<UserInfoDto> {
        return ApiFactory.apiService.getUserInfo(login)
    }

    override fun loadData(lastId: Int): Single<List<UserShortInfoDto>> {
        return ApiFactory.apiService.getListUsers(lastID = lastId)
    }


}