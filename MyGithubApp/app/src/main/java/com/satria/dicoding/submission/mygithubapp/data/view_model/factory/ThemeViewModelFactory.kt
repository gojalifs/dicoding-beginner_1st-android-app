package com.satria.dicoding.submission.mygithubapp.data.view_model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.satria.dicoding.submission.mygithubapp.data.view_model.ThemeViewModel
import com.satria.dicoding.submission.mygithubapp.settings.SettingPreferences

class ThemeViewModelFactory(private val pref: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
    }
}