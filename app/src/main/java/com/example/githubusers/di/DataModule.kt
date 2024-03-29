package com.example.githubusers.di

import android.app.Application
import com.example.githubusers.data.RepositoryImpl
import com.example.githubusers.data.database.AppDatabase
import com.example.githubusers.data.database.UserInfoDao
import com.example.githubusers.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    companion object{
        @Provides
        fun provideUserInfoDao(
            application: Application
        ): UserInfoDao{
            return AppDatabase.getInstance(application).userInfoDao()
        }
    }

}