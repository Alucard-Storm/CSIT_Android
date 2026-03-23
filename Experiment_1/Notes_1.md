# Practical 1 – Android Studio Setup and Hello World Application

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [Back to Experiment 1](./Experiment_1.md)

---

## What is Android?

Android is an open-source mobile operating system developed by Google, based on the **Linux kernel**. It is designed primarily for touchscreen devices like smartphones and tablets. Android is written in Java, Kotlin, and C/C++, and it powers over 3 billion active devices worldwide.

---

## Android Architecture

Android is organized into five layers, stacked from bottom to top:

### 1. Linux Kernel
The foundation of the Android platform. It handles core system services such as memory management, process management, security, and hardware drivers (camera, display, WiFi, Bluetooth).

### 2. Hardware Abstraction Layer (HAL)
Provides standard interfaces that expose device hardware capabilities to the higher-level Java API framework. When the framework API makes a call to access device hardware, Android loads the library module for that hardware component.

### 3. Android Runtime (ART) and Native Libraries
ART is the managed runtime used by Android applications. It replaced the older Dalvik Virtual Machine starting from Android 5.0 (Lollipop). ART uses **AOT (Ahead-of-Time) compilation**, which compiles the entire bytecode into native machine code at install time, unlike Dalvik's JIT (Just-in-Time) approach which compiled at runtime. This makes ART significantly faster at execution.

Native libraries (written in C/C++) include WebKit (browser engine), OpenGL ES (graphics), SQLite (database), and Media framework.

### 4. Application Framework
A set of APIs written in Java that developers use to build apps. Key services include:
- **Activity Manager** — manages the lifecycle of activities
- **Window Manager** — manages the display windows
- **Content Providers** — manages shared data between apps
- **Notification Manager** — manages status bar notifications
- **Package Manager** — manages installed applications

### 5. Applications
The top layer where user-facing apps run — including both system apps (Dialer, Contacts, Camera) and third-party apps installed from the Play Store.

---

## Android Studio

Android Studio is the official Integrated Development Environment (IDE) for Android application development, built on JetBrains' IntelliJ IDEA. It provides:
- A visual **Layout Editor** for designing UIs
- A **Code Editor** with Android-specific autocompletion
- An **Android Virtual Device (AVD) Manager** for creating emulators
- An integrated **Logcat** for viewing device logs
- **Gradle-based** build automation

---

## Gradle

Gradle is the build system used by Android Studio. It reads configuration from `build.gradle` files and handles compiling source code, packaging resources, generating the APK (or AAB), and managing dependencies. Every Android project has two Gradle files:
- **Project-level `build.gradle`** — global settings, classpath plugins
- **Module-level `build.gradle`** — app-specific settings like `minSdk`, `targetSdk`, and dependencies

---

## AndroidManifest.xml

Every Android application must have an `AndroidManifest.xml` file at the root of its source tree. This file describes essential information about the app to the Android build tools, the Android OS, and the Google Play Store:
- The app's **package name** (unique identifier)
- All **components** (Activities, Services, Receivers, Providers)
- Required **permissions** (e.g., INTERNET, CAMERA)
- **Minimum and target SDK** versions
- The **launcher activity** (entry point)

---

## R.java

`R.java` is an auto-generated Java class created by the Android build tools. It maps every resource file (layouts, drawables, strings, IDs) to a unique integer constant. This allows Java/Kotlin code to reference resources by name at compile time. Developers should never edit `R.java` manually — it is regenerated every time the project is built.

**Example:**
- `R.layout.activity_main` → integer ID for `res/layout/activity_main.xml`
- `R.id.btnSubmit` → integer ID for a Button with `android:id="@+id/btnSubmit"`
- `R.string.app_name` → integer ID for the string in `res/values/strings.xml`
