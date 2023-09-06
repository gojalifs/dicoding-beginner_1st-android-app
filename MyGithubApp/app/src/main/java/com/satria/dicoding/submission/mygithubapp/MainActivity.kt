package com.satria.dicoding.submission.mygithubapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.satria.dicoding.submission.mygithubapp.data.response.UserResponse
import com.satria.dicoding.submission.mygithubapp.data.view_model.UserViewModel
import com.satria.dicoding.submission.mygithubapp.databinding.ActivityMainBinding
import com.satria.dicoding.submission.mygithubapp.ui.profile.SectionPagerAdapter
import com.satria.dicoding.submission.mygithubapp.ui.search.SearchActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userViewModel by viewModels<UserViewModel>()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.followers,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btn_search -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }


        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager2
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs

        userViewModel.user.observe(this) {
            setUserData(it)
            /*
                set the tab title
             */
            val follows: MutableList<String> = mutableListOf("${it?.following}", "${it?.followers}")
            for (i in follows.indices) {
                val tab = binding.tabs.getTabAt(i)
                tab?.text = "${resources.getString(TAB_TITLES[i])} ${follows[i]}"
            }
        }

        userViewModel.isLoading.observe(this) { showLoading(it) }
        userViewModel.toastMessage.observe(this) {
            it.getContentIfNotHandled().let { msg ->
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
        }

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.shimmerProfile.visibility = View.VISIBLE
            binding.includeProfileHeader.root.visibility = View.INVISIBLE
        } else {
            binding.shimmerProfile.visibility = View.INVISIBLE
            binding.includeProfileHeader.root.visibility = View.VISIBLE
        }
    }


    private fun setUserData(user: UserResponse?) {
        val userBinding = binding.includeProfileHeader
        userBinding.tvFullName.text = user?.name
        userBinding.tvUsername.text = getString(R.string.username, user?.login)
        userBinding.tvCountry.text = user?.location
        userBinding.tvEmail.text = user?.email
        Glide.with(this).load(user?.avatarUrl).into(userBinding.imgAvatar)
    }
}
