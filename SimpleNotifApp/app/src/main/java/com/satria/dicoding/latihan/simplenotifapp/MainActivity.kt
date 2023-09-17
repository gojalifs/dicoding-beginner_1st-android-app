package com.satria.dicoding.latihan.simplenotifapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.satria.dicoding.latihan.simplenotifapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "notif_channel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        val title = getString(R.string.notification_title)
        val msg = getString(R.string.notification_message)

        binding.btnSendNotification.setOnClickListener { sendNotification(title, msg) }
        binding.btnOpenDetail.setOnClickListener {
            val detailIntent = Intent(this, DetailActivity::class.java)
            detailIntent.putExtra(DetailActivity.EXTRA_TITLE, title)
            detailIntent.putExtra(DetailActivity.EXTRA_MESSAGE, msg)
            startActivity(detailIntent)
        }
    }

    private fun sendNotification(title: String, msg: String) {
        val notifDetailIntent = Intent(this, DetailActivity::class.java)
        notifDetailIntent.putExtra(DetailActivity.EXTRA_TITLE, title)
        notifDetailIntent.putExtra(DetailActivity.EXTRA_MESSAGE, msg)

        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(notifDetailIntent)
            getPendingIntent(
                NOTIFICATION_ID,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(msg)
            .setSubText(getString(R.string.notification_subtext))
            .setSmallIcon(R.drawable.round_notifications_active_24)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.round_notifications_active_24
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Notifications permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Notifications Permission Rejected", Toast.LENGTH_SHORT).show()
        }
    }
}