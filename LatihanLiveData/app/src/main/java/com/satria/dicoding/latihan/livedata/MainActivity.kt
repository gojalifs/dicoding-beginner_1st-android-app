package com.satria.dicoding.latihan.livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.satria.dicoding.latihan.livedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var liveDataTimerViewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        liveDataTimerViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        subscribe()
    }

    private fun subscribe() {
        val elapsedTimeObserver = Observer<Long?> {
            val newText = this.resources.getString(R.string.seconds, it)
            activityMainBinding.timerTextview.text = newText
        }

        liveDataTimerViewModel.getElapsedData().observe(this, elapsedTimeObserver)
    }
}