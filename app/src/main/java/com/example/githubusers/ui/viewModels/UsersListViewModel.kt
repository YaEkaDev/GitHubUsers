package com.example.githubusers.ui.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubusers.data.mappers.UserMapper
import com.example.githubusers.data.network.ApiFactory
import com.example.githubusers.data.models.UserInfo
import com.example.githubusers.data.models.UserShortInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


class UsersListViewModel(application: Application) : AndroidViewModel(application) {

    private val mapper = UserMapper()
    private val compositeDisposable = CompositeDisposable() //коллекция всех подписок

    private val _usersList = MutableLiveData<List<UserShortInfo>>()
    val usersList: LiveData<List<UserShortInfo>>
        get() = _usersList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo>
        get() = _userInfo

    private var lastId: Int = INIT_ID

    init {
        loadData()
    }

    fun loadData() {

        if (_isLoading.value == true) {
            return
        }

        val disposable =
            ApiFactory.apiService.getListUsers(lastID = lastId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(Consumer {
                    _isLoading.value = true
                })
                .doAfterTerminate(Action {
                    _isLoading.value = false
                })
                .subscribe({

                    val list = mapper.mapListShortInfoDtoToListEntity(it)
                    lastId = list[list.lastIndex].id
                    Log.d("MyLog", lastId.toString())

                    if (_usersList.value == null) {
                        _usersList.value = list
                    } else {
                        val loadUsers =
                            _usersList.value as MutableList<UserShortInfo> //получаем список уже загруженных данных
                        loadUsers.addAll(list) //дополняем список новыми данными
                        _usersList.setValue(loadUsers) //кладем в лайвдату расширенный список
                    }
                },
                    {
                        Log.d("UserViewModel", "Throw: $it")
                    })

        compositeDisposable.add(disposable)
    }

    fun getUserDetailInfo(login: String) {

        val disposable = ApiFactory.apiService.getUserInfo(login)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _userInfo.value = mapper.mapDtoToEntity(it)
            },
                {
                    Log.d("UserViewModel", "Throw: $it")
                })

        compositeDisposable.add(disposable)

    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    companion object {
        private const val INIT_ID = 0
    }

}