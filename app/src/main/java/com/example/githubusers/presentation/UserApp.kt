package com.example.githubusers.presentation

import android.app.Application
import com.example.githubusers.di.DaggerApplicationComponent

class UserApp : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}