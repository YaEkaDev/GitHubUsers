package com.example.githubusers.di

import androidx.lifecycle.ViewModel
import com.example.githubusers.presentation.viewModels.FavouriteUsersViewModel
import com.example.githubusers.presentation.viewModels.UserInfoViewModel
import com.example.githubusers.presentation.viewModels.UsersListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UsersListViewModel::class)
    fun bindUsersListViewModel(viewModel: UsersListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserInfoViewModel::class)
    fun bindUserInfoViewModel(viewModel: UserInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteUsersViewModel::class)
    fun bindFavouriteUsersViewModel(viewModel: FavouriteUsersViewModel): ViewModel

}