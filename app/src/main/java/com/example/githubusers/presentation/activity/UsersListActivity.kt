package com.example.githubusers.presentation.activity

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
import com.example.githubusers.domain.models.UserShortInfo
import com.example.githubusers.presentation.UserApp
import com.example.githubusers.presentation.adapters.UserShortInfoAdapter
import com.example.githubusers.presentation.viewModels.UsersListViewModel
import com.example.githubusers.presentation.viewModels.ViewModelFactory
import javax.inject.Inject

class UsersListActivity : AppCompatActivity() {

    private val component by lazy {
        (application as UserApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UsersListViewModel::class.java]
    }
    private lateinit var adapter: UserShortInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        viewModelObserve()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.setLastId()
            viewModel.loadData()
        }

    }

    private fun viewModelObserve() {

        viewModel.usersList.observe(this, Observer {
            if (it==null){
                return@Observer
            }
            binding.swipeRefreshLayout.isRefreshing = false
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