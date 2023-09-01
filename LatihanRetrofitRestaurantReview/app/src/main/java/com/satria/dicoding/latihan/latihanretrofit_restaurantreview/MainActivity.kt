package com.satria.dicoding.latihan.latihanretrofit_restaurantreview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.satria.dicoding.latihan.latihanretrofit_restaurantreview.data.response.CustomerReviewsItem
import com.satria.dicoding.latihan.latihanretrofit_restaurantreview.data.response.PostReviewResponse
import com.satria.dicoding.latihan.latihanretrofit_restaurantreview.data.response.Restaurant
import com.satria.dicoding.latihan.latihanretrofit_restaurantreview.data.response.RestaurantResponse
import com.satria.dicoding.latihan.latihanretrofit_restaurantreview.data.retrofit.ApiConfig
import com.satria.dicoding.latihan.latihanretrofit_restaurantreview.databinding.ActivityMainBinding
import com.satria.dicoding.latihan.latihanretrofit_restaurantreview.ui.MainViewModel
import com.satria.dicoding.latihan.latihanretrofit_restaurantreview.ui.ReviewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]


        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        mainViewModel.restaurant.observe(this) { setRestaurantData(it) }
        mainViewModel.reviews.observe(this) { setReviewData(it) }
        mainViewModel.isLoading.observe(this) { showLoading(it) }

        binding.btnSend.setOnClickListener { view ->
            mainViewModel.postReview(binding.edReview.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun setReviewData(consumerReviews: List<CustomerReviewsItem?>?) {
        val adapter = ReviewAdapter()
        adapter.submitList(consumerReviews)
        binding.rvReview.adapter = adapter
        binding.edReview.setText("")
    }

    private fun setRestaurantData(restaurant: Restaurant?) {
        binding.tvTitle.text = restaurant?.name
        binding.tvDescription.text = restaurant?.description
        Glide.with(this)
            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant?.pictureId}")
            .into(binding.ivPicture)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}