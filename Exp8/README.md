# Experiment 8: Message Alert Notifications

## Overview
This experiment demonstrates **push notifications** and **message alerts** in Android using the **Notification API**. It shows how to create notification channels, build notifications, and handle user interactions with notifications.

## Key Concepts

### 1. Notification Channels
- Required for Android 8.0+ (API level 26+)
- Organize notifications by importance level
- User-configurable notification settings
- Channel ID, name, and description

### 2. Notification Building
- NotificationCompat.Builder for backward compatibility
- Setting title, message, and icons
- Small icon and large icon configuration
- Priority levels for notification handling

### 3. Notification Content
- Basic text message
- Small and large icons
- Auto-cancel behavior
- Action buttons and intents

### 4. Pending Intents
- Define actions when notification is clicked
- Launch activities or services from notifications
- Flag handling for intent behavior

### 5. Notification Display
- NotificationManager for showing notifications
- Notification ID for tracking
- Notification dismissal and interaction

## What You'll Learn

1. ✓ How to create notification channels
2. ✓ How to build notifications with NotificationCompat.Builder
3. ✓ How to display notifications to users
4. ✓ How to set up notification click actions
5. ✓ How to handle different Android versions
6. ✓ How to create user-friendly alerts

## Main Activity
- **Alert.java** - Main activity with notification implementation

## How to Run

1. Open the project in Android Studio
2. Build and run on an emulator or physical device (Android 8.0+)
3. Use the UI to trigger notifications
4. View notifications in the notification panel
5. Click notifications to trigger defined actions

## Files Structure
```
Exp8/
├── app/src/main/
│   ├── java/com/storm/exp8/
│   │   └── Alert.java
│   ├── res/layout/
│   │   └── activity_alert.xml
│   ├── res/drawable/
│   │   └── notification_icon.png
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Permissions Required
```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

## Related Android Concepts
- Notifications: `NotificationCompat.Builder`, `NotificationManager`
- Channels: `NotificationChannel` (API 26+)
- Intents: `PendingIntent` for notification actions
- UI Components: `EditText`, `Button` for input
- Intent Handling: Launch activities from notifications
