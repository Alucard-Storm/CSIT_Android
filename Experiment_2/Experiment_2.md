# Experiment 2 – Activity Lifecycle Demonstration

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [← Experiment 1](../Experiment_1/Experiment_1.md) | [Next → Experiment 3](../Experiment_3/Experiment_3.md)

> [**Notes**](./Notes_2.md)

---

## Aim
To develop an application that logs and displays activity lifecycle events during orientation changes and background transitions.

## Objective
- Understand all seven Activity lifecycle callback methods
- Log lifecycle events using `Log.d()` and observe in Logcat
- Display live lifecycle status on screen

## Prerequisites / Theory

### Activity Lifecycle
Every Android Activity passes through a defined sequence of states managed by the OS.

```
         ┌─────────────────────────────────────────┐
         │              App Launched                │
         └──────────────────┬──────────────────────┘
                            ↓
                       onCreate()        ← Initialize UI, restore state
                            ↓
                       onStart()         ← Activity becomes visible
                            ↓
                       onResume()        ← Activity is interactive (RUNNING)
                            ↓
              ┌─────────────────────────────┐
              │         User interaction     │
              └─────────────────────────────┘
                            ↓
                       onPause()         ← Another activity overlaps (save data here)
                            ↓
                       onStop()          ← Activity fully hidden (release resources)
                            ↓
                      onDestroy()        ← Activity removed from memory
```

### Key Scenarios
| Scenario | Lifecycle callbacks triggered |
|---|---|
| App opens | `onCreate → onStart → onResume` |
| Home button pressed | `onPause → onStop` |
| Back to app | `onRestart → onStart → onResume` |
| Screen rotated | `onPause → onStop → onDestroy → onCreate → onStart → onResume` |
| Back button | `onPause → onStop → onDestroy` |

### onSaveInstanceState
Called before `onStop()`. Use it to save UI state that should survive rotation:
```java
@Override
protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString("event_log", tvLog.getText().toString());
}
```

---

## Procedure
1. Create a new project
2. Override all lifecycle methods in `MainActivity`
3. In each method, append the event name to a `TextView` and log it with `Log.d()`
4. Test by pressing Home, rotating the screen, and returning to the app

---

## Code

**activity_main.xml:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Activity Lifecycle Demo"
        android:textSize="20sp"
        android:textStyle="bold"
        android:gravity="center"
        android:layout_marginBottom="12dp"/>

    <TextView
        android:id="@+id/tvCurrentState"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Current State: —"
        android:textSize="16sp"
        android:textColor="#6200EE"
        android:layout_marginBottom="8dp"/>

    <Button
        android:id="@+id/btnClear"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Clear Log"
        android:layout_marginBottom="8dp"/>

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1">

        <TextView
            android:id="@+id/tvLog"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Lifecycle events will appear here...\n"
            android:textSize="13sp"
            android:fontFamily="monospace"
            android:padding="8dp"
            android:background="#F5F5F5"/>
    </ScrollView>

</LinearLayout>
```

**MainActivity.java:**
```java
package com.example.lifecycledemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LifecycleDemo";
    private TextView tvLog, tvCurrentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLog          = findViewById(R.id.tvLog);
        tvCurrentState = findViewById(R.id.tvCurrentState);
        Button btnClear = findViewById(R.id.btnClear);

        // Restore log after rotation
        if (savedInstanceState != null) {
            tvLog.setText(savedInstanceState.getString("event_log", ""));
        }

        btnClear.setOnClickListener(v -> tvLog.setText("Log cleared.\n"));

        logEvent("onCreate", "Activity created" +
            (savedInstanceState != null ? " (after rotation)" : " (fresh)"));
    }

    @Override protected void onStart() {
        super.onStart();
        logEvent("onStart", "Activity visible");
    }

    @Override protected void onResume() {
        super.onResume();
        logEvent("onResume", "Activity interactive");
    }

    @Override protected void onPause() {
        super.onPause();
        logEvent("onPause", "Activity partially hidden");
    }

    @Override protected void onStop() {
        super.onStop();
        logEvent("onStop", "Activity fully hidden");
    }

    @Override protected void onRestart() {
        super.onRestart();
        logEvent("onRestart", "Activity coming back from stopped");
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        // Only log to Logcat — views may be invalid after onDestroy
        Log.d(TAG, "→ onDestroy()  [Activity destroyed]\n");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("event_log", tvLog.getText().toString());
    }

    private void logEvent(String method, String description) {
        String entry = "→ " + method + "()  [" + description + "]\n";
        Log.d(TAG, entry);
        tvLog.append(entry);
        tvCurrentState.setText("Current State: " + method + "()");
    }
}
```

---

## Output
The app shows a scrollable log of lifecycle events. As you press Home, return to the app, or rotate the screen, new entries appear. Logcat (filter by `LifecycleDemo`) shows the same entries. After rotation, the log is restored via `onSaveInstanceState`.

## Viva Questions
1. List all Activity lifecycle methods in order.
2. What is the difference between `onPause()` and `onStop()`?
3. What happens to the lifecycle when the screen is rotated?
4. What is `onSaveInstanceState()`? When is it called?
5. Why is `onPause()` the safest place to save critical data?
6. What is `onRestart()`? When is it called?

## Result
An Android application demonstrating all Activity lifecycle callbacks with live on-screen logging and state restoration after rotation was successfully implemented.
