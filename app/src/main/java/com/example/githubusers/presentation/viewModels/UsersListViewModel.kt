package com.example.githubusers.presentation.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.mappers.UserMapper
import com.example.githubusers.domain.models.UserInfo
import com.example.githubusers.domain.models.UserShortInfo
import com.example.githubusers.domain.useCases.GetUserDetailInfoUseCase
import com.example.githubusers.domain.useCases.LoadDataUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class UsersListViewModel @Inject constructor(
    private val loadDataUseCase : LoadDataUseCase,
    private val getUserDetailInfoUseCase : GetUserDetailInfoUseCase,
    private val mapper: UserMapper
) : ViewModel() {

   // private val mapper = UserMapper()
    private val compositeDisposable = CompositeDisposable()

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

    fun setLastId(){
        lastId = INIT_ID
        _usersList.value = null
    }

    fun loadData() {

        if (_isLoading.value == true) {
            return
        }

        val disposable = loadDataUseCase(lastId)
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

                    if (_usersList.value == null) { //если это первая загрузка
                        _usersList.value = list
                    } else {
                        val loadUsers =
                            _usersList.value as MutableList<UserShortInfo> //получаем список уже загруженных данных
                        loadUsers.addAll(list) //дополняем список новыми данными
                        _usersList.setValue(loadUsers) //кладем в лайвдату расширенный список
                    }
                },
                    {
                        Log.d("Errors", "loadData: $it")
                    })

        compositeDisposable.add(disposable)

    }

    fun getUserDetailInfo(login: String) {

        val disposable = getUserDetailInfoUseCase(login)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _userInfo.value = mapper.mapDtoToEntity(it)
            },
                {
                    Log.d("Errors", "getUserDetailInfo: $it")
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