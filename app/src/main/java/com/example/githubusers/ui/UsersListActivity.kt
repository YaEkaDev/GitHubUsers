package com.example.githubusers.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.githubusers.R
import com.example.githubusers.databinding.ActivityMainBinding
import com.example.githubusers.data.models.UserShortInfo
import com.example.githubusers.ui.adapters.UserShortInfoAdapter
import com.example.githubusers.ui.viewModels.UsersListViewModel

class UsersListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UsersListViewModel
    private lateinit var adapter: UserShortInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        viewModel = ViewModelProvider(this)[UsersListViewModel::class.java]
        viewModelObserve()

    }

    private fun viewModelObserve() {

        viewModel.usersList.observe(this, Observer {
            adapter.submitList(it)
            Log.d("MyLog", it.toString())
        })

        viewModel.isLoading.observe(this, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.userInfo.observe(this, Observer {
            val intent = UserInfoActivity.newIntent(
                this@UsersListActivity,
                it
            )
            startActivity(intent)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemFavourite){
            val intent = FavouriteUsersActivity.newIntent(this)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {

        adapter = UserShortInfoAdapter(this)
        binding.rvUsersList.adapter = adapter

        adapter.onReachEndListener  = object : UserShortInfoAdapter.OnReachEndListener{
            override fun onReachEnd() {
                viewModel.loadData()
            }

        }

        adapter.onUserClickListener = object : UserShortInfoAdapter.OnUserClickListener{
            override fun onUserClick(userShortInfo: UserShortInfo) {
                viewModel.getUserDetailInfo(userShortInfo.login)
            }
        }
    }

}