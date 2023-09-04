package com.satria.dicoding.submission.mygithubapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.satria.dicoding.submission.mygithubapp.data.response.FollowResponseItem
import com.satria.dicoding.submission.mygithubapp.data.response.UserResponse
import com.satria.dicoding.submission.mygithubapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> = _user

    private val _following = MutableLiveData<FollowResponseItem>()
    val following: LiveData<FollowResponseItem> = _following

    private val _follower = MutableLiveData<FollowResponseItem>()
    val follower: LiveData<FollowResponseItem> = _follower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "UserViewModel"
        private const val USER_ID = ""
    }

    init {
        getUser()
    }

    private fun getUser() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUser("gojalifs")
        client.enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _user.value = responseBody
                        // TODO implement get follows
                    }
                } else {
                    when (response.code()) {
                        400 -> Log.e(TAG, "onFailure: ${response.message()}")
                    }
                    // TODO show toast, later
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}