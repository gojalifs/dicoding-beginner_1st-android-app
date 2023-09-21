package com.satria.dicoding.submission.mygithubapp.ui.favorites_user

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.satria.dicoding.submission.mygithubapp.data.response.UserResponse
import com.satria.dicoding.submission.mygithubapp.data.view_model.FavoriteUsersViewModel
import com.satria.dicoding.submission.mygithubapp.data.view_model.factory.ViewModelFactory
import com.satria.dicoding.submission.mygithubapp.databinding.ActivityFavoritesUserBinding
import com.satria.dicoding.submission.mygithubapp.ui.profile.ListUserAdapter

class FavoritesUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesUserBinding
    private lateinit var adapter: ListUserAdapter
    private val favoriteUsersViewModel by viewModels<FavoriteUsersViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ListUserAdapter()
        binding.rvFavorites.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorites.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorites.addItemDecoration(itemDecoration)

        favoriteUsersViewModel.getAllFavs().observe(this) { users ->
            val favUsers = arrayListOf<UserResponse>()
            users.map {
                val item = UserResponse(login = it.username, avatarUrl = it.avatarUrl)
                favUsers.add(item)
                adapter.submitList(favUsers)
            }

            if (users.isEmpty()) {
                binding.tvNoData.visibility = View.VISIBLE
                binding.rvFavorites.visibility = View.GONE
            } else {
                binding.tvNoData.visibility = View.INVISIBLE
                binding.rvFavorites.visibility = View.VISIBLE
            }
        }
    }

//    override fun onRestart() {
//        adapter = ListUserAdapter()
//        favoriteUsersViewModel.getAllFavs().observe(this) { users ->
//            val favUsers = arrayListOf<UserResponse>()
//            users.map {
//                val item = UserResponse(login = it.username, avatarUrl = it.avatarUrl)
//                favUsers.add(item)
//                adapter.submitList(favUsers)
//            }
//
//            if (users.isEmpty()) {
//                binding.tvNoData.visibility = View.VISIBLE
//                binding.rvFavorites.visibility = View.INVISIBLE
//            } else {
//                binding.tvNoData.visibility = View.INVISIBLE
//                binding.rvFavorites.visibility = View.GONE
//            }
//        }
//        super.onRestart()
//    }
}