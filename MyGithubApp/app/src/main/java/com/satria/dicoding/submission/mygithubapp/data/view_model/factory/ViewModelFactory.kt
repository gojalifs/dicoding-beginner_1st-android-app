package com.satria.dicoding.submission.mygithubapp.data.view_model.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.satria.dicoding.submission.mygithubapp.data.view_model.FavoriteUsersViewModel
import com.satria.dicoding.submission.mygithubapp.data.view_model.ThemeViewModel
import com.satria.dicoding.submission.mygithubapp.settings.SettingPreferences
import com.satria.dicoding.submission.mygithubapp.settings.dataStore

class ViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteUsersViewModel::class.java)) {
            return FavoriteUsersViewModel(mApplication) as T
        }
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(SettingPreferences.getInstance(mApplication.dataStore)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
    }
}