# Experiment 9: Background Task Execution using WorkManager

## Aim

Develop an application that performs background tasks such as delayed notifications using WorkManager.

## Objective

To learn how to schedule and execute deferrable background work.

## Outcome

Students will be able to implement reliable background processing even if the app is closed.

## Process Flow

1. Add `androidx.work:work-runtime` dependency.
2. Create a `Worker` class that extends `Worker` and override `doWork()`.
3. In `MainActivity.java`, create a `OneTimeWorkRequest` or `PeriodicWorkRequest`.
4. Enqueue the request using `WorkManager.getInstance(context).enqueue(request)`.
5. Observe the status of the work using `getWorkInfoByIdLiveData`.

## Code

### `MyWorker.java`

```java
package com.csit.experiment_9;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Do the work here--in this case, upload the images.
        uploadImages();

        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }

    private void uploadImages() {
        // Upload logic here
    }
}
```

### `MainActivity.java`

```java
package com.csit.experiment_9;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WorkRequest uploadWorkRequest =
            new OneTimeWorkRequest.Builder(MyWorker.class)
                .build();

        WorkManager
            .getInstance(this)
            .enqueue(uploadWorkRequest);
    }
}
```
