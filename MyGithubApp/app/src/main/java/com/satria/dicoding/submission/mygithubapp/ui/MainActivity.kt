package com.satria.dicoding.submission.mygithubapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.satria.dicoding.submission.mygithubapp.R
import com.satria.dicoding.submission.mygithubapp.data.response.UserResponse
import com.satria.dicoding.submission.mygithubapp.data.view_model.FavoriteUsersViewModel
import com.satria.dicoding.submission.mygithubapp.data.view_model.UserViewModel
import com.satria.dicoding.submission.mygithubapp.data.view_model.ViewModelFactory
import com.satria.dicoding.submission.mygithubapp.database.FavoriteUser
import com.satria.dicoding.submission.mygithubapp.databinding.ActivityMainBinding
import com.satria.dicoding.submission.mygithubapp.ui.favorites_user.FavoritesUserActivity
import com.satria.dicoding.submission.mygithubapp.ui.profile.SectionPagerAdapter
import com.satria.dicoding.submission.mygithubapp.ui.search.SearchActivity

class MainActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private val userViewModel by viewModels<UserViewModel>()
    private val favoriteUserViewModel by viewModels<FavoriteUsersViewModel> {
        ViewModelFactory.getInstance(
            application
        )
    }

    private var favoriteUser = FavoriteUser()
    private var isFavorites = false
    private val user = "gojalifs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBar.setOnMenuItemClickListener(this)

        val username = intent.getStringExtra(EXTRA_USERNAME) ?: user

        if (user != username) {
            binding.fabFav.visibility = View.VISIBLE
            binding.fabFav.setOnClickListener {
                if (isFavorites) {
                    favoriteUserViewModel.delete(favoriteUser)
                    showToast("User Deleted From Favorites")
                } else {
                    favoriteUserViewModel.insert(favoriteUser)
                    showToast("User Added To Favorites")
                }
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
            showToast(it.getContentIfNotHandled())
        }

        favoriteUserViewModel.getFavUserByUsername().observe(this) { user ->
            isFavorites = if (user == null) {
                binding.fabFav.setImageResource(R.drawable.baseline_favorite_border_24)
                false
            } else {
                binding.fabFav.setImageResource(R.drawable.baseline_favorite_24)
                true
            }
        }

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                shimmerProfile.visibility = View.VISIBLE
                includeProfileHeader.root.visibility = View.INVISIBLE
                binding.fabFav.isEnabled = false
            } else {
                shimmerProfile.visibility = View.INVISIBLE
                includeProfileHeader.root.visibility = View.VISIBLE
                binding.fabFav.isEnabled = true
            }
        }
    }


    private fun setUserData(user: UserResponse?) {
        val userBinding = binding.includeProfileHeader
        userBinding.tvFullName.text = user?.name
        userBinding.tvUsername.text = getString(R.string.username, user?.login)
        userBinding.tvCountry.text = user?.location
        if (user?.email == null) {
            userBinding.tvEmail.visibility = View.GONE
            userBinding.icMail.visibility = View.GONE
        } else {
            userBinding.tvEmail.text = user.email
        }
        if (user?.location == null){
            userBinding.tvCountry.text = "Location Not Set"
        }
        Glide.with(this).load(user?.avatarUrl).into(userBinding.imgAvatar)

        /*
            initiate user to be favorited
         */
        favoriteUser.username = user?.login ?: ""
        favoriteUser.avatarUrl = user?.avatarUrl
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.followers,
        )

    }

    private fun showToast(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.btn_favorites -> {
                val intent = Intent(this, FavoritesUserActivity::class.java)
                startActivity(intent)
                true
            }

            else -> false
        }
    }
}
