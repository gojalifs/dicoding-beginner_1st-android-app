package com.satria.dicoding.latihan.roomapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.satria.dicoding.latihan.roomapp.R
import com.satria.dicoding.latihan.roomapp.databinding.ActivityMainBinding
import com.satria.dicoding.latihan.roomapp.helper.ViewModelFactory
import com.satria.dicoding.latihan.roomapp.ui.insert.NoteManagerActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = obtainViewModel(this@MainActivity)
        mainViewModel.getAllNotes().observe(this) {
            if (it != null) {
                adapter.setListNotes(it)
            }
        }

        adapter = NoteAdapter()
        with(binding) {
            // recyclerview init
            rvNotes.layoutManager = LinearLayoutManager(this@MainActivity)
            rvNotes.setHasFixedSize(true)
            rvNotes.adapter = adapter

            // fab init
            fabAdd.setOnClickListener {
                val intent = Intent(this@MainActivity, NoteManagerActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun obtainViewModel(activity: MainActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }
}