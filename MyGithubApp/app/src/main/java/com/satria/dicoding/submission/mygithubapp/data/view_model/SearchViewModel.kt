package com.satria.dicoding.submission.mygithubapp.data.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.satria.dicoding.submission.mygithubapp.data.response.SearchResponse
import com.satria.dicoding.submission.mygithubapp.data.response.UserResponse
import com.satria.dicoding.submission.mygithubapp.data.retrofit.ApiConfig
import com.satria.dicoding.submission.mygithubapp.util.Event
import retrofit2.Call
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val _searchList = MutableLiveData<List<UserResponse?>?>()
    val searchList: LiveData<List<UserResponse?>?> = _searchList

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> = _toastMessage

    companion object {
        private const val TAG = "SearchViewModel"
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


    fun searchUser(user: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().searchUser(user)
        client.enqueue(object : retrofit2.Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _searchList.value = responseBody.items
                    }
                } else {
                    setToastMsg(response.code())
                }

                _isLoading.value = false
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}