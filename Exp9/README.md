# Experiment 9: Multi-threading

## Overview
This experiment demonstrates **multi-threading** in Android to perform background operations without blocking the main UI thread. It shows how to create background threads, update the UI from background threads, and track progress with progress bars.

## Key Concepts

### 1. Background Threading
- Creating and starting background threads
- Runnable interface for thread execution
- Separating heavy operations from UI thread
- Preventing ANR (Application Not Responding) errors

### 2. UI Thread Updates
- Handler for posting UI updates from background threads
- Looper for message queue management
- Thread-safe UI manipulation
- MainLooper for main UI thread access

### 3. Progress Tracking
- ProgressBar widget for visual feedback
- Updating progress from background thread
- Real-time progress indication
- Progress percentage calculation

### 4. Thread Synchronization
- Coordinating UI and background operations
- Status text updates during processing
- Proper thread lifecycle management

### 5. Handler Communication
- Handler.post() for UI thread execution
- Thread-safe communication pattern
- Message passing between threads

## What You'll Learn

1. ✓ How to create background threads
2. ✓ How to use Handler for thread communication
3. ✓ How to update UI from background threads safely
4. ✓ How to implement progress tracking
5. ✓ How to prevent UI blocking
6. ✓ How to handle long-running operations

## Main Activity
- **Threading.java** - Main activity with background threading implementation

## How to Run

1. Open the project in Android Studio
2. Build and run on an emulator or physical device
3. Click the button to start background operation
4. Watch the progress bar update
5. See status text change as operation progresses
6. UI remains responsive during operation

## Files Structure
```
Exp9/
├── app/src/main/
│   ├── java/com/storm/exp9/
│   │   └── Threading.java
│   ├── res/layout/
│   │   └── activity_threading.xml
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Related Android Concepts
- Threading: `Thread`, `Runnable` interface
- UI Updates: `Handler`, `Looper`, `MainLooper`
- Progress: `ProgressBar` widget
- Synchronization: Thread-safe operations
- UI Components: `Button`, `TextView`, `ProgressBar`
