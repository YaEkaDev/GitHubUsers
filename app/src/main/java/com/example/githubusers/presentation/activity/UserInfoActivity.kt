package com.example.githubusers.presentation.activity

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.githubusers.databinding.ActivityUserDetailInfoBinding
import com.example.githubusers.domain.models.UserInfo
import com.example.githubusers.presentation.UserApp
import com.example.githubusers.presentation.viewModels.UserInfoViewModel
import com.example.githubusers.presentation.viewModels.ViewModelFactory
import com.squareup.picasso.Picasso
import javax.inject.Inject

class UserInfoActivity : AppCompatActivity() {

    private val component by lazy {
        (application as UserApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by lazy {
        ActivityUserDetailInfoBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UserInfoViewModel::class.java]
    }

    private lateinit var userInfo: UserInfo


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        parseIntent()
        viewModelObserve()
    }

    private fun viewModelObserve() {

            val starOff = ContextCompat.getDrawable(this, R.drawable.star_big_off)
            val starOn = ContextCompat.getDrawable(this, R.drawable.star_big_on)

            viewModel.getFavouriteUser(userInfo.id).observe(this, Observer { favouriteUser->

                if (favouriteUser==null){
                    binding.ivFavourite.setImageDrawable(starOff)
                    binding.ivFavourite.setOnClickListener {
                        viewModel.insertFavouriteUser(userInfo)
                    }
                } else {
                    binding.ivFavourite.setImageDrawable(starOn)
                    binding.ivFavourite.setOnClickListener {
                        viewModel.removeFavouriteUser(userInfo.id)
                    }
                }
            })

    }

    private fun parseIntent(){

        if (!intent.hasExtra(EXTRA_USER_INFO)){
            finish()
            return
        }
        userInfo = intent.getSerializableExtra(EXTRA_USER_INFO) as UserInfo

        with(binding) {
            tvLogin.text = userInfo.login
            tvName.text = userInfo.name
            tvEmail.text = userInfo.email
            tvCompany.text = userInfo.company
            tvFollowers.text = userInfo.followers_count.toString()
            tvFollowing.text = userInfo.following_count.toString()
            tvCreatedAt.text = userInfo.dateCreating
            Picasso.get().load(userInfo.avatarUrl).into(ivAvatar)
        }

    }

    companion object{

        private const val EXTRA_USER_INFO = "user_info"
        fun newIntent(context: Context, userInfo: UserInfo): Intent {
            val intent = Intent(context, UserInfoActivity::class.java)
            intent.putExtra(EXTRA_USER_INFO, userInfo)
            return intent
        }

    }
}
