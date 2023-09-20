package com.satria.dicoding.submission.mygithubapp.data.retrofit

import com.satria.dicoding.submission.mygithubapp.BuildConfig
import com.satria.dicoding.submission.mygithubapp.data.response.SearchResponse
import com.satria.dicoding.submission.mygithubapp.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("users/{user}")
    fun getUser(@Path("user") user: String): Call<UserResponse>

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("users/{user}/following")
    fun getFollowing(@Path("user") user: String): Call<List<UserResponse>>

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("users/{user}/followers")
    fun getFollower(@Path("user") user: String): Call<List<UserResponse>>

    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    @GET("search/users")
    fun searchUser(@Query("q") user: String): Call<SearchResponse>
}