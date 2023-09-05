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
import com.satria.dicoding.submission.mygithubapp.databinding.FragmentFollowBinding
import com.satria.dicoding.submission.mygithubapp.ui.UserViewModel


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var username: String
    private val userViewModel: UserViewModel by activityViewModels()
    private var position = 1

    companion object {
        var ARG_POSITION = "position"
        var ARG_USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        binding = FragmentFollowBinding.inflate(inflater, container, false)
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        val adapter = ListFollowsAdapter()
        binding.rvUsers.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: ""
        }
        Log.d("TAG", "onViewCreated: position $position")

        if (position == 1) {
            userViewModel.following.observe(viewLifecycleOwner) { setFollowsData(it) }
        } else {
            userViewModel.follower.observe(viewLifecycleOwner) { setFollowsData(it) }
        }

        // TODO set data here


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
}