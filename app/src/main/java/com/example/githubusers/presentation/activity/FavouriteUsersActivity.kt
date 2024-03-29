package com.example.githubusers.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.githubusers.databinding.ActivityFavouriteUsersBinding
import com.example.githubusers.domain.models.UserInfo
import com.example.githubusers.presentation.UserApp
import com.example.githubusers.presentation.adapters.UserInfoAdapter
import com.example.githubusers.presentation.viewModels.FavouriteUsersViewModel
import com.example.githubusers.presentation.viewModels.ViewModelFactory
import javax.inject.Inject

class FavouriteUsersActivity : AppCompatActivity() {

    private val component by lazy {
        (application as UserApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by lazy {
        ActivityFavouriteUsersBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FavouriteUsersViewModel::class.java]
    }

    private lateinit var adapter: UserInfoAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        viewModel.getFavouriteUsers().observe(this, Observer {
            adapter.favouriteUsersList = it
        })

    }

    private fun setupRecyclerView() {

        adapter = UserInfoAdapter(this)
        binding.rvFavouriteUsers.adapter = adapter

        adapter.onUserClickListener = object : UserInfoAdapter.OnUserClickListener{
            override fun onUserClick(userInfo: UserInfo) {
                val intent = UserInfoActivity.newIntent(
                    this@FavouriteUsersActivity,
                    userInfo
                )
                startActivity(intent)
            }
        }
    }

    companion object{
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, FavouriteUsersActivity::class.java)
            return intent
        }
    }
}