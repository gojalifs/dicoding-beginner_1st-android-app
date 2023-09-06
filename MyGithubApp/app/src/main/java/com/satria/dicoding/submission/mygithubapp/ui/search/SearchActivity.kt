package com.satria.dicoding.submission.mygithubapp.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.satria.dicoding.submission.mygithubapp.data.response.UserResponse
import com.satria.dicoding.submission.mygithubapp.data.view_model.SearchViewModel
import com.satria.dicoding.submission.mygithubapp.databinding.ActivitySearchBinding
import com.satria.dicoding.submission.mygithubapp.ui.profile.ListFollowsAdapter

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel.searchList.observe(this) { setSearchResult(it) }
        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.toastMessage.observe(this) {
            it.getContentIfNotHandled().let { msg ->
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
        }

        val adapter = ListFollowsAdapter()
        binding.rvSearch.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.rvSearch.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvSearch.addItemDecoration(itemDecoration)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.text = searchView.text
                searchView.hide()
                viewModel.searchUser(searchBar.text.toString())
                false
            }
        }
    }

    private fun setSearchResult(searchResult: List<UserResponse?>?) {
        val adapter = ListFollowsAdapter()
        adapter.submitList(searchResult)
        binding.rvSearch.adapter = adapter

        if (searchResult != null) {
            if (searchResult.isEmpty()) {
                binding.tvNoData.visibility = View.VISIBLE
            } else {
                binding.tvNoData.visibility = View.INVISIBLE
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {

        if (isLoading) {
            binding.shimmerItemSearch.visibility = View.VISIBLE
            binding.rvSearch.visibility = View.INVISIBLE
        } else {
            binding.shimmerItemSearch.visibility = View.INVISIBLE
            binding.rvSearch.visibility = View.VISIBLE
        }
    }
}