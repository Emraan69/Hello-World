package com.notificationapp

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Worker class for handling scheduled notifications using WorkManager
 */
class NotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Scheduled Notification"
        val message = inputData.getString("message") ?: "Your scheduled notification is here!"

        val notificationHelper = NotificationHelper(applicationContext)
        notificationHelper.sendScheduledNotification(title, message)

        return Result.success()
    }
}
