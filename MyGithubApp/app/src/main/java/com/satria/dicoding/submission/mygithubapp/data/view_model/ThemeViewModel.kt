package com.satria.dicoding.submission.mygithubapp.data.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.satria.dicoding.submission.mygithubapp.settings.SettingPreferences
import kotlinx.coroutines.launch

class ThemeViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch { pref.saveThemeSetting(isDarkMode) }
    }
}