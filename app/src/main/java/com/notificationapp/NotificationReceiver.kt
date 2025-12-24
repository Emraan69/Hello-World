package com.notificationapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat

/**
 * Broadcast receiver for handling notification actions
 */
class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            "ACTION_REPLY" -> {
                Toast.makeText(context, "Reply action clicked", Toast.LENGTH_SHORT).show()
                // Cancel the notification after action
                NotificationManagerCompat.from(context).cancel(NotificationHelper.NOTIFICATION_ID_ACTION)
            }
            "ACTION_MARK_READ" -> {
                Toast.makeText(context, "Marked as read", Toast.LENGTH_SHORT).show()
                // Cancel the notification after action
                NotificationManagerCompat.from(context).cancel(NotificationHelper.NOTIFICATION_ID_ACTION)
            }
            "com.notificationapp.ACTION_NOTIFICATION" -> {
                // Handle scheduled notification trigger
                val title = intent.getStringExtra("title") ?: "Scheduled Notification"
                val message = intent.getStringExtra("message") ?: "This is a scheduled notification"
                
                val notificationHelper = NotificationHelper(context)
                notificationHelper.sendScheduledNotification(title, message)
            }
        }
    }
}
