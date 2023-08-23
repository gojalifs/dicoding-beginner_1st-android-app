package com.satria.dicoding.latihanrecyclerview

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.satria.dicoding.latihanrecyclerview.data.Hero

class HeroDetailActivity : AppCompatActivity() {
    private lateinit var tvName: TextView

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_detail)

        tvName = findViewById(R.id.tv_detail_name)

        val hero = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DATA, Hero::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }

        tvName.text = hero?.name ?: "Unknown Name"
    }
}