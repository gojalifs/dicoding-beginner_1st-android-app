package com.satria.dicoding.submission.latihannavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.satria.dicoding.submission.latihannavigation.databinding.FragmentDetailCategoryBinding


class DetailCategoryFragment : Fragment() {
    private var _binding: FragmentDetailCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataName = DetailCategoryFragmentArgs.fromBundle(arguments as Bundle).name
        val dataStock = DetailCategoryFragmentArgs.fromBundle(arguments as Bundle).stock

        binding.tvCategoryName.text = dataName
        binding.tvCategoryDescription.text = getString(R.string.stock, "$dataStock")

        binding.btnProfile.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_detailCategoryFragment_to_homeFragment2)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}