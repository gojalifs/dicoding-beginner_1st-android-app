package com.satria.dicoding.latihan.broadcastreceiverapp

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.satria.dicoding.latihan.broadcastreceiverapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var downloadReceiver: BroadcastReceiver

    companion object {
        const val ACTION_DOWNLOAD_STATUS = "download_status"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        downloadReceiver = object :BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                Toast.makeText(context, "Downlaod Finished", Toast.LENGTH_SHORT).show()
            }
        }
        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)
        registerReceiver(downloadReceiver, downloadIntentFilter)

        binding.btnPermission.setOnClickListener(this)
        binding.btnDownload.setOnClickListener(this)
    }

    private var requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "SMS receiver permission diterima", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Sms receiver permission ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_permission -> requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
            R.id.btn_download -> {
                // simulate download process in 3 seconds
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        val notifyFinishIntent = Intent().setAction(ACTION_DOWNLOAD_STATUS)
                        sendBroadcast(notifyFinishIntent)
                    }, 3000
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
    }
}