package com.satria.dicoding.latihan.sharedpreferences

import android.content.Context

internal class UserPreferences(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val AGE = "age"
        private const val PHONE_NUMBER = "phone"
        private const val LOVE_MU = "isLove"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(user: UserModel) {
        val editor = preferences.edit()
        editor.putString(NAME, user.name)
        editor.putString(EMAIL, user.email)
        editor.putInt(AGE, user.age)
        editor.putString(PHONE_NUMBER, user.phoneNumber)
        editor.putBoolean(LOVE_MU, user.isLove)
        editor.apply()
    }

    fun getUser(): UserModel {
        val user = UserModel()
        user.name = preferences.getString(NAME, "")
        user.email = preferences.getString(EMAIL, "")
        user.age = preferences.getInt(AGE, 0)
        user.phoneNumber = preferences.getString(PHONE_NUMBER, "")
        user.isLove = preferences.getBoolean(LOVE_MU, false)

        return user
    }
}