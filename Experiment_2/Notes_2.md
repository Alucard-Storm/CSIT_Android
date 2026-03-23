# Practical 2 – Activity Lifecycle Demonstration

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [Back to Experiment 2](./Experiment_2.md)

---

## What is an Activity?

An **Activity** is one of the four fundamental Android application components. It represents a **single screen with a user interface**. Every screen the user sees in an app is typically one Activity. For example, a messaging app may have one Activity for the inbox list and another for composing a message.

---

## The Activity Lifecycle

Android manages Activity instances through a well-defined lifecycle. The OS calls specific **callback methods** as an Activity transitions between states. Understanding the lifecycle is critical for saving data, releasing resources, and maintaining a smooth user experience.

```
         App Launched
              ↓
         onCreate()        ← Initialize UI, restore state
              ↓
         onStart()         ← Activity becomes visible
              ↓
         onResume()        ← Activity is interactive (RUNNING)
              ↓
         onPause()         ← Another activity partially covers this one
              ↓
         onStop()          ← Activity fully hidden
              ↓
         onDestroy()       ← Activity removed from memory
```

---

## The Seven Lifecycle Callbacks

### `onCreate(Bundle savedInstanceState)`
Called when the Activity is first created. This is where you initialize the UI (`setContentView()`), bind views using `findViewById()`, set up click listeners, and restore any previously saved state from the `Bundle`. This method is called only once per Activity instance.

### `onStart()`
Called when the Activity becomes visible to the user, but is not yet interactive. At this point, the Activity is in the foreground but the user cannot interact with it yet.

### `onResume()`
Called when the Activity starts interacting with the user. The Activity is now at the top of the activity stack and fully interactive. This is the state where the app is "running." You should start animations, open sensors (camera, GPS), and resume paused operations here.

### `onPause()`
Called when the system is about to start resuming a previous Activity, or when another Activity partially covers the current one (e.g., a dialog box). The Activity is still visible but partially obscured. This is the most important callback for **saving lightweight state** because it is the only callback guaranteed to be called before the process may be killed. Heavy operations should not be done here as it must complete quickly.

### `onStop()`
Called when the Activity is no longer visible to the user (completely hidden by another Activity). Here you should release resources that consume significant battery or memory, such as the camera, location updates, or large Bitmap objects.

### `onRestart()`
Called after `onStop()` when the user returns to this Activity from the back stack. It is always followed by `onStart()`.

### `onDestroy()`
Called before the Activity is destroyed. This happens either when the user finishes the Activity (presses Back) or when the system destroys it due to configuration changes (screen rotation) or to reclaim memory. Final cleanup should happen here.

---

## Key Scenarios

| Scenario | Callbacks Triggered |
|---|---|
| App opens | `onCreate → onStart → onResume` |
| Home button pressed | `onPause → onStop` |
| Return to app | `onRestart → onStart → onResume` |
| Screen rotated | `onPause → onStop → onDestroy → onCreate → onStart → onResume` |
| Back button pressed | `onPause → onStop → onDestroy` |

---

## Configuration Changes and Rotation

When the device is **rotated**, Android destroys the current Activity instance and creates a new one. This means the full lifecycle runs: `onPause → onStop → onDestroy → onCreate → onStart → onResume`. Any data held in instance variables is lost unless explicitly saved.

---

## Saving and Restoring State

To preserve UI state across rotation, override `onSaveInstanceState(Bundle outState)` to save data into a Bundle before the Activity is destroyed. The same Bundle is then passed to `onCreate()` and `onRestoreInstanceState()` when the Activity is recreated.

```java
@Override
protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString("key", value);
}

// Restore in onCreate:
if (savedInstanceState != null) {
    value = savedInstanceState.getString("key");
}
```

---

## Logcat

Logcat is Android's system logging tool. Developers write log messages using static methods on the `Log` class:
- `Log.d(TAG, "message")` — Debug
- `Log.i(TAG, "message")` — Info
- `Log.e(TAG, "message")` — Error
- `Log.w(TAG, "message")` — Warning

The TAG is typically the class name, used to filter messages in the Logcat panel of Android Studio.
