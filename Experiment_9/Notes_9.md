# Practical 9 – Background Task Execution using WorkManager

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [Back to Experiment 9](./Experiment_9.md)

---

## Background Processing in Android

Android aggressively manages resources to preserve battery life. The OS applies **battery optimizations** (Doze Mode, App Standby) that can suspend background processes when the device is idle or the app is not in use. This makes running reliable background work challenging without the right tools.

---

## What is WorkManager?

`WorkManager` is a Jetpack library for scheduling **deferrable, guaranteed background work**. "Guaranteed" means the work will execute even if the app exits or the device is restarted. WorkManager persists work requests to a database and re-enqueues them after a reboot.

Internally, WorkManager selects the best implementation based on the API level:
- **API 23+** — uses `JobScheduler` (system-managed, battery-efficient)
- **API < 23** — uses a combination of `BroadcastReceiver` + `AlarmManager`

---

## When to Use WorkManager

WorkManager is designed for work that:
- **Must be completed** regardless of app lifecycle
- Is **deferrable** — does not need to run at an exact second
- May have **constraints** (network required, charging, etc.)

Examples of good use cases:
- Uploading logs or analytics to a server
- Syncing data with a cloud database
- Compressing images before upload
- Sending a scheduled reminder notification

WorkManager is **not appropriate** for:
- Immediate work that runs while the user is interacting (use `ExecutorService` or Kotlin Coroutines)
- Exact-time alarms like a clock app (use `AlarmManager`)
- Long-running foreground tasks like music playback (use a `ForegroundService`)

---

## Worker

The `Worker` class is the actual unit of work. You extend it and override `doWork()`. This method runs **automatically on a background thread** — you do not need to manage threads yourself.

```java
public class MyWorker extends Worker {

    public MyWorker(Context context, WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        // Do background work here
        String input = getInputData().getString("key");

        // ... perform the task ...

        return Result.success(); // or failure() or retry()
    }
}
```

### Return Values
| Return | Meaning |
|---|---|
| `Result.success()` | Work completed successfully |
| `Result.failure()` | Work failed; do not retry |
| `Result.retry()` | Work failed; WorkManager will retry with backoff |

---

## WorkRequest

A `WorkRequest` wraps a `Worker` and configures how and when it should run.

### OneTimeWorkRequest
Executes the Worker exactly once:
```java
OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class)
    .setInitialDelay(5, TimeUnit.SECONDS)
    .setInputData(inputData)
    .setConstraints(constraints)
    .build();

WorkManager.getInstance(context).enqueue(request);
```

### PeriodicWorkRequest
Executes the Worker repeatedly. The **minimum interval is 15 minutes** (enforced by the OS):
```java
PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(
    MyWorker.class, 15, TimeUnit.MINUTES)
    .build();
```

---

## Constraints

Constraints define **conditions that must be met** before WorkManager will run the work. The work is deferred until all constraints are satisfied:

```java
Constraints constraints = new Constraints.Builder()
    .setRequiredNetworkType(NetworkType.CONNECTED) // any active network
    .setRequiresCharging(true)                     // device must be charging
    .setRequiresBatteryNotLow(true)                // battery not critically low
    .setRequiresStorageNotLow(true)                // storage not critically low
    .build();
```

`NetworkType` options: `CONNECTED`, `UNMETERED` (WiFi only), `NOT_REQUIRED`, `METERED`.

---

## Input and Output Data

The `Data` class is a **lightweight key-value map** for passing information to and from a Worker.

Passing input to a Worker:
```java
Data inputData = new Data.Builder()
    .putString("message", "Hello Worker!")
    .putInt("count", 5)
    .build();
```

Returning output from a Worker:
```java
Data outputData = new Data.Builder()
    .putString("result", "Task completed!")
    .build();
return Result.success(outputData);
```

---

## Observing Work Status

WorkManager exposes a `LiveData<WorkInfo>` for each WorkRequest, identified by its unique `UUID`. You observe this in an Activity to update the UI as the work progresses.

```java
workManager.getWorkInfoByIdLiveData(request.getId())
    .observe(this, workInfo -> {
        if (workInfo != null) {
            WorkInfo.State state = workInfo.getState();
            // state can be: ENQUEUED, RUNNING, SUCCEEDED, FAILED, CANCELLED, BLOCKED
            if (state == WorkInfo.State.SUCCEEDED) {
                String result = workInfo.getOutputData().getString("result");
            }
        }
    });
```

### Work States

| State | Meaning |
|---|---|
| `ENQUEUED` | Work is queued, waiting to run |
| `RUNNING` | `doWork()` is currently executing |
| `SUCCEEDED` | `doWork()` returned `Result.success()` |
| `FAILED` | `doWork()` returned `Result.failure()` |
| `CANCELLED` | Work was cancelled via `cancelWorkById()` |
| `BLOCKED` | Work is part of a chain and a prerequisite hasn't finished |
