package com.notificationapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/**
 * Helper class for managing all notification operations
 * Provides methods for creating different types of notifications
 */
class NotificationHelper(private val context: Context) {

    private val notificationManager = NotificationManagerCompat.from(context)

    companion object {
        // Channel IDs
        const val CHANNEL_DEFAULT = "default_channel"
        const val CHANNEL_IMPORTANT = "important_channel"
        const val CHANNEL_UPDATES = "updates_channel"
        const val CHANNEL_REMINDERS = "reminders_channel"
        
        // Notification IDs
        const val NOTIFICATION_ID_SIMPLE = 1
        const val NOTIFICATION_ID_BIG_TEXT = 2
        const val NOTIFICATION_ID_BIG_PICTURE = 3
        const val NOTIFICATION_ID_INBOX = 4
        const val NOTIFICATION_ID_ACTION = 5
        const val NOTIFICATION_ID_PROGRESS = 6
    }

    init {
        createNotificationChannels()
    }

    /**
     * Create all notification channels
     * Required for Android 8.0 (API 26) and above
     */
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(
                    CHANNEL_DEFAULT,
                    context.getString(R.string.channel_default_name),
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = context.getString(R.string.channel_default_description)
                    enableLights(true)
                    lightColor = Color.BLUE
                    enableVibration(true)
                },
                NotificationChannel(
                    CHANNEL_IMPORTANT,
                    context.getString(R.string.channel_important_name),
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = context.getString(R.string.channel_important_description)
                    enableLights(true)
                    lightColor = Color.RED
                    enableVibration(true)
                    setShowBadge(true)
                },
                NotificationChannel(
                    CHANNEL_UPDATES,
                    context.getString(R.string.channel_updates_name),
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = context.getString(R.string.channel_updates_description)
                },
                NotificationChannel(
                    CHANNEL_REMINDERS,
                    context.getString(R.string.channel_reminders_name),
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = context.getString(R.string.channel_reminders_description)
                    enableVibration(true)
                }
            )

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            channels.forEach { manager.createNotificationChannel(it) }
        }
    }

    /**
     * Send a simple notification
     */
    fun sendSimpleNotification() {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_DEFAULT)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID_SIMPLE, notification)
    }

    /**
     * Send a big text notification with expanded content
     */
    fun sendBigTextNotification() {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText(context.getString(R.string.notification_big_text))
            .setBigContentTitle("Expanded Notification")
            .setSummaryText("Tap to read more")

        val notification = NotificationCompat.Builder(context, CHANNEL_DEFAULT)
            .setSmallIcon(R.drawable.ic_text)
            .setContentTitle("Big Text Notification")
            .setContentText(context.getString(R.string.notification_message))
            .setStyle(bigTextStyle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID_BIG_TEXT, notification)
    }

    /**
     * Send an inbox style notification showing multiple lines
     */
    fun sendInboxNotification() {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val inboxStyle = NotificationCompat.InboxStyle()
            .addLine("Message 1: You have a new notification")
            .addLine("Message 2: Check out the latest updates")
            .addLine("Message 3: Don't miss important information")
            .addLine("Message 4: Tap to view all messages")
            .addLine("Message 5: Stay connected and informed")
            .setBigContentTitle("5 New Messages")
            .setSummaryText("+2 more")

        val notification = NotificationCompat.Builder(context, CHANNEL_DEFAULT)
            .setSmallIcon(R.drawable.ic_inbox)
            .setContentTitle("Inbox Style")
            .setContentText("You have 5 new messages")
            .setStyle(inboxStyle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID_INBOX, notification)
    }

    /**
     * Send a notification with action buttons
     */
    fun sendActionNotification() {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Action buttons
        val replyIntent = Intent(context, NotificationReceiver::class.java).apply {
            action = "ACTION_REPLY"
        }
        val replyPendingIntent = PendingIntent.getBroadcast(
            context, 0, replyIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val markReadIntent = Intent(context, NotificationReceiver::class.java).apply {
            action = "ACTION_MARK_READ"
        }
        val markReadPendingIntent = PendingIntent.getBroadcast(
            context, 1, markReadIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_IMPORTANT)
            .setSmallIcon(R.drawable.ic_action)
            .setContentTitle("Action Notification")
            .setContentText("This notification has action buttons")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_action, context.getString(R.string.action_reply), replyPendingIntent)
            .addAction(R.drawable.ic_action, context.getString(R.string.action_mark_read), markReadPendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID_ACTION, notification)
    }

    /**
     * Send a progress notification
     */
    fun sendProgressNotification(progress: Int = 0) {
        val notification = NotificationCompat.Builder(context, CHANNEL_DEFAULT)
            .setSmallIcon(R.drawable.ic_progress)
            .setContentTitle("Downloading")
            .setContentText("Download in progress")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(100, progress, false)
            .setOngoing(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID_PROGRESS, notification)
    }

    /**
     * Update progress notification
     */
    fun updateProgressNotification(progress: Int) {
        val notification = NotificationCompat.Builder(context, CHANNEL_DEFAULT)
            .setSmallIcon(R.drawable.ic_progress)
            .setContentTitle("Downloading")
            .setContentText("$progress% complete")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(100, progress, false)
            .setOngoing(progress < 100)
            .build()

        notificationManager.notify(NOTIFICATION_ID_PROGRESS, notification)
    }

    /**
     * Send a scheduled notification
     */
    fun sendScheduledNotification(title: String, message: String) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_REMINDERS)
            .setSmallIcon(R.drawable.ic_schedule)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    /**
     * Cancel a notification by ID
     */
    fun cancelNotification(notificationId: Int) {
        notificationManager.cancel(notificationId)
    }

    /**
     * Cancel all notifications
     */
    fun cancelAllNotifications() {
        notificationManager.cancelAll()
    }
}
