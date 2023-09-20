package com.satria.dicoding.submission.mygithubapp.repository

import android.app.Application
import com.satria.dicoding.submission.mygithubapp.database.FavoriteUser
import com.satria.dicoding.submission.mygithubapp.database.FavoritesUserDao
import com.satria.dicoding.submission.mygithubapp.database.FavoritesUserRoomDatabase
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavsUserDao: FavoritesUserDao
    private val executorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoritesUserRoomDatabase.getDatabase(application)
        mFavsUserDao = db.favoriteDao()
    }

    fun getAllFavorites() = mFavsUserDao.getAllFavoritesUser()

    fun getFavoriteUserByUsername(username: String) =
        mFavsUserDao.getFavoriteUserByUsername(username)

    fun insert(user: FavoriteUser) {
        executorService.execute { mFavsUserDao.insert(user) }
    }

    fun delete(user: FavoriteUser) {
        executorService.execute { mFavsUserDao.delete(user) }
    }
}