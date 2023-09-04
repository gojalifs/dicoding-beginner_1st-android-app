package com.satria.dicoding.submission.mygithubapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.satria.dicoding.submission.mygithubapp.data.response.UserResponse
import com.satria.dicoding.submission.mygithubapp.databinding.ActivityMainBinding
import com.satria.dicoding.submission.mygithubapp.ui.UserViewModel
import com.satria.dicoding.submission.mygithubapp.ui.profile.SectionPagerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userViewModel by viewModels<UserViewModel>()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.follower,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel.user.observe(this) { setUserData(it) }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager2
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setUserData(user: UserResponse?) {
        val userBinding = binding.includeProfileHeader
        userBinding.tvFullName.text = user?.name
        userBinding.tvUsername.text = user?.login
        userBinding.tvCountry.text = user?.location
        userBinding.tvEmail.text = user?.email
        Glide.with(this).load(user?.avatarUrl).into(userBinding.imgAvatar)

    }
}