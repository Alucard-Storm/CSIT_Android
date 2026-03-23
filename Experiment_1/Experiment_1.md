# Experiment 1 – Android Studio Setup and Hello World Application

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [Next → Experiment 2](../Experiment_2/Experiment_2.md)

> [**Notes**](./Notes_1.md)

---

## Aim
To install Android Studio, configure the SDK and Emulator, and develop a basic Android application displaying a welcome message.

## Objective
- Install Android Studio and JDK 17
- Configure Android SDK and create an AVD
- Create, build, and run a Hello World Android application

## Prerequisites / Theory

### Android Studio
Official IDE for Android, built on IntelliJ IDEA. Provides a visual layout editor, code completion, integrated debugger, and emulator.

### Key Terms
| Term | Meaning |
|---|---|
| **SDK** | Software Development Kit — APIs and tools to build Android apps |
| **AVD** | Android Virtual Device — emulator simulating an Android phone |
| **Gradle** | Build automation tool that compiles code and creates the APK |
| **APK** | Android Package — the installable app file |
| **`R.java`** | Auto-generated class mapping resource names to integer IDs |

### Project Structure
```
app/src/main/
├── java/com/example/helloworld/
│   └── MainActivity.java
├── res/
│   ├── layout/activity_main.xml
│   └── values/strings.xml
└── AndroidManifest.xml
```

---

## Procedure

### Step 1: Install JDK 17
Download from [https://adoptium.net](https://adoptium.net) and set `JAVA_HOME`.

### Step 2: Install Android Studio
1. Download from [https://developer.android.com/studio](https://developer.android.com/studio)
2. During setup select: Android SDK, Android Virtual Device
3. Finish the setup wizard

### Step 3: Create a New Project
1. Open Android Studio → **New Project → Empty Views Activity**
2. Set Name: `HelloWorldApp`, Package: `com.example.helloworld`
3. Language: **Java**, Min SDK: **API 26**, Click **Finish**

### Step 4: Create an AVD
1. Tools → **Device Manager → Create Virtual Device**
2. Select: Pixel 6 → API 34 → Next → Finish
3. Launch the emulator (▶ button)

### Step 5: Run the App
1. Press `Shift + F10` or click the green Run button
2. Select the running AVD
3. App launches showing "Hello World!"

---

## Code

**activity_main.xml:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/welcomeText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Welcome to Android Programming!"
        android:textSize="20sp"
        android:textStyle="bold"
        android:gravity="center"
        android:padding="16dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

**MainActivity.java:**
```java
package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setOnClickListener(v ->
            Toast.makeText(this, "Hello, RGPV Student!", Toast.LENGTH_SHORT).show()
        );
    }
}
```

---

## Output
The app launches on the emulator showing "Welcome to Android Programming!" centered on screen. Tapping the text shows a Toast message.

## Viva Questions
1. What is Android Studio? What build tool does it use?
2. What is an AVD? How is it different from a physical device?
3. What does `setContentView()` do?
4. What is `R.java`? Can you edit it manually?
5. What is the role of `AndroidManifest.xml`?

## Result
Android Studio was successfully installed, configured, and a Hello World application was developed and executed on the AVD.
