package com.satria.dicoding.submission.mygithubapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.satria.dicoding.submission.mygithubapp.data.response.UserResponse
import com.satria.dicoding.submission.mygithubapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val _user = MutableLiveData<UserResponse?>()
    val user: LiveData<UserResponse?> = _user

    private val _following = MutableLiveData<List<UserResponse>?>()
    val following: LiveData<List<UserResponse>?> = _following

    private val _follower = MutableLiveData<List<UserResponse>?>()
    val follower: LiveData<List<UserResponse>?> = _follower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "UserViewModel"
        private const val USER_ID = "gojalifs"
    }

    init {
        getUser()
        getFollowing()
        getFollower()
    }

    private fun getUser() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUser(USER_ID)
        client.enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _user.value = responseBody
                    }
                } else {
                    when (response.code()) {
                        400 -> Log.e(TAG, "onFailure: ${response.message()}")
                    }
                    // TODO show toast, later
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun getFollower() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollower(USER_ID)
        client.enqueue(object : retrofit2.Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _follower.value = responseBody
                        Log.d(TAG, "get follower")
                        Log.d(TAG, responseBody.toString())
                    }
                } else {
                    when (response.code()) {
                        400 -> Log.e(TAG, "onFailure: ${response.message()}")
                    }
                    // TODO show toast, later
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    private fun getFollowing() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollowing(USER_ID)
        client.enqueue(object : retrofit2.Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _following.value = responseBody
                        Log.d(TAG, "get following")
                        Log.d(TAG, responseBody.toString())
                    }
                } else {
                    when (response.code()) {
                        400 -> Log.e(TAG, "onFailure: ${response.message()}")
                    }
                    // TODO show toast, later
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}