package com.satria.dicoding.submission.mygithubapp.data.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import com.satria.dicoding.submission.mygithubapp.database.FavoriteUser
import com.satria.dicoding.submission.mygithubapp.repository.FavoriteUserRepository

class FavoriteUsersViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllFavs() = mFavoriteUserRepository.getAllFavorites()

    fun getFavUserByUsername() = mFavoriteUserRepository.getFavoriteUserByUsername(username)

    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }

    companion object{
        var username = ""
    }
}