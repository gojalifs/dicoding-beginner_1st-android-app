package com.satria.dicoding.latihan.simplenotifapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.satria.dicoding.latihan.simplenotifapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(EXTRA_TITLE)
        val msg = intent.getStringExtra(EXTRA_MESSAGE)

        binding.tvTitle.text = title
        binding.tvMessage.text = msg
    }

    companion object{
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_MESSAGE = "extra_msg"
    }
}