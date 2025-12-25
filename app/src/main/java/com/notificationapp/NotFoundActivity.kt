package com.notificationapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

/**
 * NotFoundActivity - 404 Page
 * Displays when content is not found or navigation fails
 * Provides helpful suggestions and quick actions for users
 */
class NotFoundActivity : AppCompatActivity() {

    private lateinit var notificationHelper: NotificationHelper

    companion object {
        const val EXTRA_ERROR_MESSAGE = "extra_error_message"
        const val EXTRA_ERROR_TYPE = "extra_error_type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_found)

        // Setup toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize notification helper
        notificationHelper = NotificationHelper(this)

        // Setup views and click listeners
        setupViews()

        // Handle any error information passed via intent
        handleErrorInfo()
    }

    private fun setupViews() {
        // Go Home Button
        findViewById<MaterialButton>(R.id.btnGoHome).setOnClickListener {
            navigateToHome()
        }

        // View Notifications Button (returns to main activity)
        findViewById<MaterialButton>(R.id.btnViewNotifications).setOnClickListener {
            navigateToHome()
        }

        // Open Settings Button
        findViewById<MaterialButton>(R.id.btnOpenSettings).setOnClickListener {
            openSettings()
        }

        // Send Simple Notification Button
        findViewById<MaterialButton>(R.id.btnSendNotification).setOnClickListener {
            sendSimpleNotification()
        }

        // Schedule Reminder Button
        findViewById<MaterialButton>(R.id.btnScheduleReminder).setOnClickListener {
            scheduleReminder()
        }

        // Explore Features Button (go to main)
        findViewById<MaterialButton>(R.id.btnExploreFeatures).setOnClickListener {
            navigateToHome()
        }
    }

    private fun handleErrorInfo() {
        val errorMessage = intent.getStringExtra(EXTRA_ERROR_MESSAGE)
        val errorType = intent.getStringExtra(EXTRA_ERROR_TYPE)

        // Log or display additional error information if provided
        if (!errorMessage.isNullOrEmpty()) {
            // Could display this in a TextView or log it
            android.util.Log.d("NotFoundActivity", "Error: $errorMessage (Type: $errorType)")
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun openSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun sendSimpleNotification() {
        try {
            notificationHelper.sendSimpleNotification()
            showSuccessSnackbar(getString(R.string.notification_sent))
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.notification_error), Toast.LENGTH_SHORT).show()
        }
    }

    private fun scheduleReminder() {
        try {
            // Schedule a notification for 10 seconds from now
            notificationHelper.sendSimpleNotification()
            showSuccessSnackbar("Notification sent! Check your notification tray.")
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.notification_error), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSuccessSnackbar(message: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Return to main activity on back press
        navigateToHome()
    }
}
