package com.notificationapp

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial

/**
 * Settings Activity for managing notification preferences
 */
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Setup toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.settings)

        setupViews()
    }

    private fun setupViews() {
        // Enable notifications switch
        val switchNotifications = findViewById<SwitchMaterial>(R.id.switchEnableNotifications)
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Notifications enabled
            } else {
                // Notifications disabled
            }
        }

        // Notification sound switch
        val switchSound = findViewById<SwitchMaterial>(R.id.switchNotificationSound)
        switchSound.setOnCheckedChangeListener { _, _ ->
            // Handle sound preference
        }

        // Vibration switch
        val switchVibration = findViewById<SwitchMaterial>(R.id.switchVibration)
        switchVibration.setOnCheckedChangeListener { _, _ ->
            // Handle vibration preference
        }

        // System settings button
        findViewById<MaterialButton>(R.id.btnSystemSettings).setOnClickListener {
            openSystemNotificationSettings()
        }
    }

    private fun openSystemNotificationSettings() {
        val intent = Intent().apply {
            action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        }
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
