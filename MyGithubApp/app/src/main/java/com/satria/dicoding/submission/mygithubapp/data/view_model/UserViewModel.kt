package com.satria.dicoding.submission.mygithubapp.data.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.satria.dicoding.submission.mygithubapp.data.response.UserResponse
import com.satria.dicoding.submission.mygithubapp.data.retrofit.ApiConfig
import com.satria.dicoding.submission.mygithubapp.util.Event
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

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> = _toastMessage

    companion object {
        private const val TAG = "UserViewModel"
        var USER_ID = "gojalifs"
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

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _user.value = responseBody
                    }
                } else {
                    setToastMsg(response.code())
                }

                _isLoading.value = false
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

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _follower.value = responseBody
                    }
                } else {
                    setToastMsg(response.code())
                }

                _isLoading.value = false
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

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _following.value = responseBody
                    }
                } else {
                    setToastMsg(response.code())
                }

                _isLoading.value = false
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    private fun setToastMsg(code: Int) {
        when (code) {
            400 -> _toastMessage.value = Event("Error, Bad Request. Please Contact The Admin")
            401 -> _toastMessage.value = Event("Not Authenticated, Please Add Token")
            403 -> _toastMessage.value = Event("Forbidden. You don't Have Access")
            404 -> _toastMessage.value = Event("Data Not Found")
            500 -> _toastMessage.value = Event("Error On Server, Please Hang Tight")
            else -> _toastMessage.value = Event("Unexpected Error, Please Wait")
        }
    }
}
