package com.example.githubusers.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.githubusers.databinding.ActivityFavouriteUsersBinding
import com.example.githubusers.data.models.UserInfo
import com.example.githubusers.ui.adapters.UserInfoAdapter
import com.example.githubusers.ui.viewModels.FavouriteUsersViewModel

class FavouriteUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteUsersBinding
    private lateinit var viewModel: FavouriteUsersViewModel
    private lateinit var adapter: UserInfoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        viewModel = ViewModelProvider(this)[FavouriteUsersViewModel::class.java]
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