# 📱 Professional Notification App

A modern, feature-rich Android notification application built with Material Design 3. This app demonstrates various notification types, scheduling capabilities, and professional UX/UI design patterns.

## ✨ Features

### Notification Types
- **Simple Notifications** - Basic notification with title and content
- **Big Text Notifications** - Expanded notifications with longer text content
- **Inbox Style Notifications** - Multiple line notifications for message lists
- **Action Notifications** - Notifications with interactive action buttons
- **Progress Notifications** - Display progress for long-running operations

### Scheduling
- Schedule notifications with predefined delays (5s, 10s, 30s, 1m, 5m, 10m, 30m)
- Custom scheduling with WorkManager for reliable delivery
- Background notification delivery

### UI/UX Design
- Material Design 3 (Material You) implementation
- Dynamic color theming
- Smooth animations and transitions
- Responsive layouts for all screen sizes
- Professional card-based interface
- Permission request flow with clear explanations

## 📋 Requirements

- **Minimum SDK:** API 28 (Android 9.0)
- **Target SDK:** API 34 (Android 14)
- **Kotlin:** 1.9.20
- **Gradle:** 8.2.0

## 🛠️ Technologies Used

- **Kotlin** - Modern Android development language
- **Material Design 3** - Latest Material Design guidelines
- **AndroidX** - Modern Android components
- **WorkManager** - Reliable background task scheduling
- **Coroutines** - Asynchronous programming
- **ViewBinding** - Type-safe view access
- **Notification Channels** - Organized notification categories

## 🏗️ Architecture

The app follows modern Android development best practices:

- **NotificationHelper** - Centralized notification management
- **NotificationWorker** - Background notification scheduling
- **NotificationReceiver** - Handling notification actions
- **MainActivity** - Main UI with Material Design 3
- **SettingsActivity** - Notification preferences

## 📁 Project Structure

```
app/
├── src/main/
│   ├── java/com/notificationapp/
│   │   ├── MainActivity.kt              # Main activity with UI
│   │   ├── SettingsActivity.kt          # Settings screen
│   │   ├── NotificationHelper.kt        # Notification manager
│   │   ├── NotificationWorker.kt        # WorkManager for scheduling
│   │   └── NotificationReceiver.kt      # Broadcast receiver
│   ├── res/
│   │   ├── drawable/                    # Vector icons
│   │   ├── layout/                      # XML layouts
│   │   ├── menu/                        # Menu resources
│   │   ├── values/                      # Strings, colors, themes
│   │   └── xml/                         # Backup rules
│   └── AndroidManifest.xml
```

## 🚀 Getting Started

### Prerequisites

1. Android Studio Hedgehog (2023.1.1) or later
2. JDK 8 or higher
3. Android SDK with API 28+

### Installation

1. Clone the repository:
```bash
git clone https://github.com/Emraan69/Hello-World.git
cd Hello-World
```

2. Open the project in Android Studio

3. Sync Gradle files

4. Run the app on an emulator or physical device

### Permissions

The app requests the following permissions:
- `POST_NOTIFICATIONS` - Required for Android 13+ to show notifications
- `VIBRATE` - Optional, for notification vibration
- `SCHEDULE_EXACT_ALARM` - For precise notification scheduling
- `WAKE_LOCK` - For background notification delivery

## 📱 Screenshots

*(Add screenshots of your app here)*

## 🎨 Design Features

### Material Design 3
- Dynamic color system with light/dark theme support
- Elevated and filled card styles
- Tonal buttons with corner radius
- Material toolbar with app bar
- Switch material components

### Colors
- Primary: Purple (#6750A4)
- Secondary: Medium Purple (#625B71)
- Tertiary: Rose (#7D5260)
- Full support for dark mode

## 🔔 Notification Channels

The app creates four notification channels:

1. **Default Notifications** - General notifications
2. **Important Notifications** - High priority alerts
3. **Updates** - App updates and news
4. **Reminders** - Scheduled reminders

## 📝 Usage

### Send a Simple Notification
1. Tap "Simple Notification" button
2. Notification appears in status bar
3. Tap to open app

### Schedule a Notification
1. Choose a scheduling option (5s, 10s, or custom)
2. Notification will be delivered at the scheduled time
3. Uses WorkManager for reliability

### Configure Settings
1. Tap settings icon in toolbar
2. Toggle notification preferences
3. Open system settings for advanced options

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 👨‍💻 Author

**Emraan69**
- GitHub: [@Emraan69](https://github.com/Emraan69)

## 🙏 Acknowledgments

- Material Design 3 guidelines by Google
- Android Developers documentation
- AndroidX libraries

## 📞 Support

For support, please open an issue in the GitHub repository.

---

**Made with ❤️ using Material Design 3**
