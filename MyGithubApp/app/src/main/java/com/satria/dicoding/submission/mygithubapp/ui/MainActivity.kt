package com.satria.dicoding.submission.mygithubapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.satria.dicoding.submission.mygithubapp.R
import com.satria.dicoding.submission.mygithubapp.data.response.UserResponse
import com.satria.dicoding.submission.mygithubapp.data.view_model.FavoriteUsersViewModel
import com.satria.dicoding.submission.mygithubapp.data.view_model.ThemeViewModel
import com.satria.dicoding.submission.mygithubapp.data.view_model.UserViewModel
import com.satria.dicoding.submission.mygithubapp.data.view_model.factory.ThemeViewModelFactory
import com.satria.dicoding.submission.mygithubapp.data.view_model.factory.ViewModelFactory
import com.satria.dicoding.submission.mygithubapp.database.FavoriteUser
import com.satria.dicoding.submission.mygithubapp.databinding.ActivityMainBinding
import com.satria.dicoding.submission.mygithubapp.settings.SettingPreferences
import com.satria.dicoding.submission.mygithubapp.settings.dataStore
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
    private val themeViewModel by viewModels<ThemeViewModel> {
        ThemeViewModelFactory(SettingPreferences.getInstance(application.dataStore))
    }

    private var favoriteUser = FavoriteUser()
    private var isFavorites = false
    private var isDarkMode: Boolean? = null
    private var menuItem: MenuItem? = null
    private val user = "gojalifs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.appBar.setOnMenuItemClickListener(this)
        menuItem = binding.appBar.menu.findItem(R.id.btn_dark_mode)

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

        themeViewModel.getThemeSettings().observe(this) {
            isDarkMode = if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                menuItem?.setIcon(R.drawable.baseline_dark_mode_24)
                false
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                menuItem?.setIcon(R.drawable.baseline_light_mode_24)
                true
            }
            Log.d("SAVING", "saveThemeSetting: saving $isDarkMode")
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
        if (user?.location == null) {
            userBinding.tvCountry.text = getString(R.string.location_not_set)
        }
        Glide.with(this).load(user?.avatarUrl).into(userBinding.imgAvatar)

        /*
            initiate user to be favorited
         */
        favoriteUser.username = user?.login ?: ""
        favoriteUser.avatarUrl = user?.avatarUrl
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

            R.id.btn_dark_mode -> {
                themeViewModel.saveThemeSetting(isDarkMode ?: true)

                if (isDarkMode!!) {
                    item.setIcon(R.drawable.baseline_light_mode_24)
                } else {
                    item.setIcon(R.drawable.baseline_dark_mode_24)
                }
                true
            }

            else -> false
        }

    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.followers,
        )
    }
}
