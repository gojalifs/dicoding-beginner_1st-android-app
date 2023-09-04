package com.satria.dicoding.submission.mygithubapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.satria.dicoding.submission.mygithubapp.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var username: String
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
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: ""
        }



//        val layoutManager = LinearLayoutManager(view.context)
//        binding.rvUsers.layoutManager = layoutManager
//        val itemDecoration = DividerItemDecoration(view.context, layoutManager.orientation)
//        binding.rvUsers.addItemDecoration(itemDecoration)

        if (position == 1) {
            // TODO: following
        } else {
            // TODO: Follower
        }
    }
}