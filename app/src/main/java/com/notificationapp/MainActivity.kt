package com.notificationapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * Main Activity with Material Design 3 UI
 * Demonstrates various notification types and scheduling
 */
class MainActivity : AppCompatActivity() {

    private lateinit var notificationHelper: NotificationHelper
    private lateinit var permissionBanner: MaterialCardView
    private lateinit var btnGrantPermission: MaterialButton

    // Permission launcher for Android 13+
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
            permissionBanner.visibility = View.GONE
        } else {
            Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        // Initialize notification helper
        notificationHelper = NotificationHelper(this)

        // Setup views
        setupViews()

        // Check notification permission
        checkNotificationPermission()
    }

    private fun setupViews() {
        permissionBanner = findViewById(R.id.permissionBanner)
        btnGrantPermission = findViewById(R.id.btnGrantPermission)

        // Simple Notification
        findViewById<MaterialButton>(R.id.btnSimpleNotification).setOnClickListener {
            if (hasNotificationPermission()) {
                notificationHelper.sendSimpleNotification()
                showSuccessSnackbar("Simple notification sent")
            } else {
                requestNotificationPermission()
            }
        }

        // Big Text Notification
        findViewById<MaterialButton>(R.id.btnBigTextNotification).setOnClickListener {
            if (hasNotificationPermission()) {
                notificationHelper.sendBigTextNotification()
                showSuccessSnackbar("Big text notification sent")
            } else {
                requestNotificationPermission()
            }
        }

        // Inbox Notification
        findViewById<MaterialButton>(R.id.btnInboxNotification).setOnClickListener {
            if (hasNotificationPermission()) {
                notificationHelper.sendInboxNotification()
                showSuccessSnackbar("Inbox notification sent")
            } else {
                requestNotificationPermission()
            }
        }

        // Action Notification
        findViewById<MaterialButton>(R.id.btnActionNotification).setOnClickListener {
            if (hasNotificationPermission()) {
                notificationHelper.sendActionNotification()
                showSuccessSnackbar("Action notification sent")
            } else {
                requestNotificationPermission()
            }
        }

        // Progress Notification
        findViewById<MaterialButton>(R.id.btnProgressNotification).setOnClickListener {
            if (hasNotificationPermission()) {
                simulateProgress()
            } else {
                requestNotificationPermission()
            }
        }

        // Schedule notifications
        findViewById<MaterialButton>(R.id.btnSchedule5Sec).setOnClickListener {
            if (hasNotificationPermission()) {
                scheduleNotification(5)
                showSuccessSnackbar("Notification scheduled for 5 seconds")
            } else {
                requestNotificationPermission()
            }
        }

        findViewById<MaterialButton>(R.id.btnSchedule10Sec).setOnClickListener {
            if (hasNotificationPermission()) {
                scheduleNotification(10)
                showSuccessSnackbar("Notification scheduled for 10 seconds")
            } else {
                requestNotificationPermission()
            }
        }

        findViewById<MaterialButton>(R.id.btnScheduleCustom).setOnClickListener {
            if (hasNotificationPermission()) {
                showCustomScheduleDialog()
            } else {
                requestNotificationPermission()
            }
        }

        // Grant permission button
        btnGrantPermission.setOnClickListener {
            requestNotificationPermission()
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!hasNotificationPermission()) {
                permissionBanner.visibility = View.VISIBLE
            } else {
                permissionBanner.visibility = View.GONE
            }
        } else {
            permissionBanner.visibility = View.GONE
        }
    }

    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun simulateProgress() {
        CoroutineScope(Dispatchers.Main).launch {
            notificationHelper.sendProgressNotification(0)
            for (progress in 0..100 step 10) {
                delay(500)
                notificationHelper.updateProgressNotification(progress)
            }
            showSuccessSnackbar("Download complete!")
        }
    }

    private fun scheduleNotification(delaySeconds: Long) {
        val data = Data.Builder()
            .putString("title", "Scheduled Notification")
            .putString("message", "This notification was scheduled $delaySeconds seconds ago")
            .build()

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delaySeconds, TimeUnit.SECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private fun showCustomScheduleDialog() {
        val options = arrayOf(
            "30 seconds",
            "1 minute",
            "5 minutes",
            "10 minutes",
            "30 minutes"
        )
        val delays = longArrayOf(30, 60, 300, 600, 1800)

        MaterialAlertDialogBuilder(this)
            .setTitle("Schedule Notification")
            .setItems(options) { _, which ->
                scheduleNotification(delays[which])
                showSuccessSnackbar("Notification scheduled for ${options[which]}")
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showSuccessSnackbar(message: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_SHORT
        ).setAnchorView(R.id.permissionBanner)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
