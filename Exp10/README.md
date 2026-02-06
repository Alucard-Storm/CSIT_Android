# Experiment 10: Writing Data to SD Card

## Overview
This experiment demonstrates **external storage access** in Android for reading and writing files to the SD card. It covers **permission handling**, **file operations**, and **storage management** on external storage.

## Key Concepts

### 1. External Storage
- SD card or other external storage on device
- Shared storage accessible to all apps
- Persistent storage across app uninstalls
- Storage capacity and space management

### 2. Permissions
- READ_EXTERNAL_STORAGE for reading files
- WRITE_EXTERNAL_STORAGE for writing files
- Runtime permission requests (Android 6.0+)
- Permission checking and handling

### 3. File Operations
- Creating and opening files
- Writing text data to files
- Reading file contents
- File path construction and management

### 4. Storage Access
- Environment.getExternalStorageDirectory() for external storage path
- File class for file manipulation
- FileWriter and FileReader for I/O
- IOException handling for file operations

### 5. Storage States
- Checking external storage availability
- Handling storage permission states
- Directory creation and validation

## What You'll Learn

1. ✓ How to request external storage permissions
2. ✓ How to check storage availability
3. ✓ How to write data to external storage
4. ✓ How to read data from external storage
5. ✓ How to manage file paths correctly
6. ✓ How to handle file I/O exceptions

## Main Activity
- **SDCard.java** - Main activity with file I/O operations

## How to Run

1. Open the project in Android Studio
2. Build and run on an emulator or physical device
3. Grant storage permissions when prompted
4. Use the UI to write data to SD card
5. Read stored files using the read functionality
6. Verify files in device file explorer

## Files Structure
```
Exp10/
├── app/src/main/
│   ├── java/com/storm/exp10/
│   │   └── SDCard.java
│   ├── res/layout/
│   │   └── activity_sdcard.xml
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Permissions Required
```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

## Related Android Concepts
- File Operations: `File`, `FileWriter`, `FileReader`
- Storage: `Environment` class for storage paths
- I/O Streams: `FileInputStream`, `FileOutputStream`
- Permissions: `ActivityCompat`, `ContextCompat`
- Exception Handling: `IOException`, `FileNotFoundException`
- UI Components: `EditText`, `Button`, `TextView`
