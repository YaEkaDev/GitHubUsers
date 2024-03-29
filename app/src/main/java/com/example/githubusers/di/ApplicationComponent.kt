package com.example.githubusers.di

import android.app.Application
import com.example.githubusers.presentation.activity.FavouriteUsersActivity
import com.example.githubusers.presentation.activity.UserInfoActivity
import com.example.githubusers.presentation.activity.UsersListActivity
import dagger.BindsInstance
import dagger.Component
@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

    fun inject(activity: UsersListActivity)
    fun inject(activity: UserInfoActivity)
    fun inject(activity: FavouriteUsersActivity)

}