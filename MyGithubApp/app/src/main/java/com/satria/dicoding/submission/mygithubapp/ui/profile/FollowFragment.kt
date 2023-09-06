package com.satria.dicoding.submission.mygithubapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.satria.dicoding.submission.mygithubapp.data.response.UserResponse
import com.satria.dicoding.submission.mygithubapp.data.view_model.UserViewModel
import com.satria.dicoding.submission.mygithubapp.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var username: String
    private val userViewModel: UserViewModel by activityViewModels()
    private var position = 1

    companion object {
        var ARG_POSITION = "position"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        val adapter = ListFollowsAdapter()
        binding.rvUsers.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
        }
        Log.d("TAG", "onViewCreated: position $position")
        userViewModel.isLoading.observe(viewLifecycleOwner) { showLoading(it) }
        if (position == 1) {
            userViewModel.following.observe(viewLifecycleOwner) { setFollowsData(it) }
        } else {
            userViewModel.follower.observe(viewLifecycleOwner) { setFollowsData(it) }
        }

        val layoutManager = LinearLayoutManager(view.context)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(view.context, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

    }

    private fun setFollowsData(followsData: List<UserResponse>?) {
        val adapter = ListFollowsAdapter()
        adapter.submitList(followsData)
        binding.rvUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.shimmerItem.visibility = View.VISIBLE
            binding.rvUsers.visibility = View.INVISIBLE
        } else {
            binding.shimmerItem.visibility = View.INVISIBLE
            binding.rvUsers.visibility = View.VISIBLE
        }
    }
}