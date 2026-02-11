# Experiment 1: Android Studio Setup and Hello World Application

## Aim

Install Android Studio, configure SDK and Emulator, and develop a basic Android application displaying a welcome message.

## Objective

To install and configure Android application development tools.

## Outcome

Students will be able to set up the development environment and run a basic Android application.

## Process Flow

1. Download and install Android Studio (latest stable version).
2. Configure the Android SDK and set up an Android Virtual Device (AVD).
3. Create a new project in Android Studio.
4. Select "Empty Views Activity" (or similar depending on Studio version).
5. Name the project "Experiment_1" and set the package name to `com.csit.experiment_1`.
6. Verify the project structure including `MainActivity.java` and `activity_main.xml`.
7. Build and run the application on the emulator or a physical device.

## Code

### `MainActivity.java`

```java
package com.csit.experiment_1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
```

### `activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
