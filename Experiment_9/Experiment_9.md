# Experiment 9 – Background Task Execution using WorkManager

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [← Experiment 8](../Experiment_8/Experiment_8.md) | [Next → Experiment 10](../Experiment_10/Experiment_10.md)

> [**Notes**](./Notes_9.md)

---

## Aim
To develop an Android application that performs a background task (delayed notification) using **WorkManager**.

## Objective
- Understand when to use WorkManager vs Thread
- Create a `Worker` class with `doWork()` logic
- Build and enqueue a `OneTimeWorkRequest` with delay and constraints
- Observe work status using `WorkManager.getWorkInfoByIdLiveData()`

## Prerequisites / Theory

### Why WorkManager?
| Mechanism | Guaranteed? | Survives app kill? | Survives reboot? |
|---|---|---|---|
| `Thread` / `ExecutorService` | No | No | No |
| `Service` | Partially | Partially | No |
| **WorkManager** | **Yes** | **Yes** | **Yes** |

Use WorkManager for **deferrable, guaranteed background work**: syncing data, uploading logs, showing scheduled notifications.

### WorkManager Architecture
```
WorkRequest (OneTime / Periodic)
      ↓
WorkManager.enqueue()
      ↓
Worker.doWork()   ← runs on background thread automatically
      ↓
Result.success() / Result.failure() / Result.retry()
```

### Constraints
Work only runs when specific conditions are met:
```java
Constraints constraints = new Constraints.Builder()
    .setRequiredNetworkType(NetworkType.CONNECTED)
    .setRequiresCharging(false)
    .setRequiresBatteryNotLow(true)
    .build();
```

### OneTimeWorkRequest vs PeriodicWorkRequest
| | OneTimeWorkRequest | PeriodicWorkRequest |
|---|---|---|
| Runs | Once | Repeatedly |
| Min interval | — | 15 minutes |
| Use case | One-off upload | Daily sync |

---

## Procedure
1. Add WorkManager dependency
2. Create `NotificationWorker.java` extending `Worker`
3. Build a `OneTimeWorkRequest` with a 5-second delay
4. Observe work status using `LiveData`

---

## Code

**build.gradle (module):**
```groovy
implementation 'androidx.work:work-runtime:2.9.0'
```

**NotificationWorker.java:**
```java
package com.example.workmanagerdemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {

    static final String KEY_MESSAGE = "message";
    static final String CHANNEL_ID  = "work_channel";

    public NotificationWorker(@NonNull Context context,
                              @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // This runs on a background thread automatically
        String message = getInputData().getString(KEY_MESSAGE);
        if (message == null) message = "Background task completed!";

        showNotification(message);

        // Return output data
        Data output = new Data.Builder()
            .putString("result", "Task done: " + message)
            .build();
        return Result.success(output);
    }

    private void showNotification(String message) {
        NotificationManager nm = (NotificationManager)
            getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, "Work Notifications",
                NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
            getApplicationContext(), CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("WorkManager Task")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true);

        nm.notify(1001, builder.build());
    }
}
```

**activity_main.xml:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent" android:layout_height="match_parent"
    android:orientation="vertical" android:padding="24dp" android:gravity="center">

    <TextView android:text="WorkManager Demo"
        android:textSize="22sp" android:textStyle="bold"
        android:layout_width="wrap_content" android:layout_height="wrap_content"
        android:layout_marginBottom="32dp"/>

    <com.google.android.material.textfield.TextInputLayout
        style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
        android:layout_width="match_parent" android:layout_height="wrap_content"
        android:hint="Notification message" android:layout_marginBottom="16dp">
        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/etMessage"
            android:layout_width="match_parent" android:layout_height="wrap_content"/>
    </com.google.android.material.textfield.TextInputLayout>

    <com.google.android.material.button.MaterialButton
        android:id="@+id/btnSchedule"
        android:layout_width="match_parent" android:layout_height="wrap_content"
        android:text="Schedule (5 second delay)"
        android:layout_marginBottom="16dp"/>

    <com.google.android.material.button.MaterialButton
        android:id="@+id/btnCancel"
        style="@style/Widget.MaterialComponents.Button.OutlinedButton"
        android:layout_width="match_parent" android:layout_height="wrap_content"
        android:text="Cancel Work"
        android:layout_marginBottom="24dp"/>

    <TextView android:id="@+id/tvStatus"
        android:layout_width="match_parent" android:layout_height="wrap_content"
        android:text="Status: Idle"
        android:textSize="16sp" android:gravity="center"
        android:padding="12dp" android:background="#F0F0F0"/>
</LinearLayout>
```

**MainActivity.java:**
```java
package com.example.workmanagerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    WorkManager workManager;
    UUID workId;
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        workManager = WorkManager.getInstance(this);
        tvStatus    = findViewById(R.id.tvStatus);

        TextInputEditText etMessage  = findViewById(R.id.etMessage);
        MaterialButton btnSchedule   = findViewById(R.id.btnSchedule);
        MaterialButton btnCancel     = findViewById(R.id.btnCancel);

        btnSchedule.setOnClickListener(v -> {
            String msg = etMessage.getText() != null
                ? etMessage.getText().toString().trim() : "";
            if (msg.isEmpty()) msg = "Hello from WorkManager!";

            // Input data
            Data inputData = new Data.Builder()
                .putString(NotificationWorker.KEY_MESSAGE, msg)
                .build();

            // Constraints (optional — no network needed for notification)
            Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(false)
                .build();

            // Build the work request
            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(
                NotificationWorker.class)
                .setInitialDelay(5, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();

            workId = workRequest.getId();
            workManager.enqueue(workRequest);
            tvStatus.setText("Status: Enqueued (fires in 5s)");

            // Observe work status
            workManager.getWorkInfoByIdLiveData(workId)
                .observe(this, workInfo -> {
                    if (workInfo != null) {
                        WorkInfo.State state = workInfo.getState();
                        tvStatus.setText("Status: " + state.name());
                        if (state == WorkInfo.State.SUCCEEDED) {
                            String result = workInfo.getOutputData()
                                .getString("result");
                            tvStatus.setText("Status: SUCCEEDED\n" + result);
                        }
                    }
                });
        });

        btnCancel.setOnClickListener(v -> {
            if (workId != null) {
                workManager.cancelWorkById(workId);
                tvStatus.setText("Status: Cancelled");
            } else {
                tvStatus.setText("No work scheduled.");
            }
        });
    }
}
```

---

## Output
App with a message input and two buttons. Tapping "Schedule" enqueues work with a 5-second delay. The status label updates from `ENQUEUED → RUNNING → SUCCEEDED`. A notification appears after 5 seconds. Tapping "Cancel" before it fires shows `CANCELLED`.

## Viva Questions
1. What is WorkManager? When should you use it?
2. What is the difference between `OneTimeWorkRequest` and `PeriodicWorkRequest`?
3. What is `doWork()` and what should it return?
4. What are Constraints in WorkManager?
5. How do you observe work status in WorkManager?
6. What is the minimum interval for `PeriodicWorkRequest`?

## Result
An Android application using WorkManager to schedule a delayed background task that triggers a system notification, with live status observation via LiveData, was successfully implemented.
