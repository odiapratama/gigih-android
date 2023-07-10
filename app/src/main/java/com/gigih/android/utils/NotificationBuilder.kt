package com.gigih.android.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.gigih.android.presentation.MainActivity
import com.gigih.android.R
import kotlin.random.Random

class NotificationBuilder(
    private val context: Context
) {

    companion object {
        const val CHANNEL_ID = "scheduled_notification"
        const val CHANNEL_NAME = "Gigih Android"
        const val CHANNEL_DESCRIPTION = "Scheduled Notification"
    }

    fun build() {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Scheduled notification")
            .setContentText("Hello from one-time worker!")
            .setStyle(
                NotificationCompat.BigTextStyle().bigText("Hello from one-time worker!")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.description = CHANNEL_DESCRIPTION
            notificationChannel.enableVibration(true)
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notify(Random.nextInt(), builder.build())
            }
        }
    }
}