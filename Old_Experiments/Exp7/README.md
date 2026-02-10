# Experiment 7: GPS Location

## Overview
This experiment demonstrates **GPS location services** in Android using the **Fused Location Provider Client**. It shows how to request location permissions, handle location updates, and display GPS coordinates.

## Key Concepts

### 1. Location Services
- FusedLocationProviderClient for location requests
- More efficient than raw LocationManager
- Provides accurate location through multiple sources
- Battery-optimized location tracking

### 2. Runtime Permissions
- Requesting location permissions at runtime (Android 6.0+)
- Handling permission results
- LocationPermission.FINE_LOCATION and COARSE_LOCATION
- Permission checks before accessing location

### 3. Location Updates
- Requesting single location updates
- Continuous location updates with LocationRequest
- Update frequency and accuracy settings
- Location callbacks through OnSuccessListener

### 4. Location Data
- Latitude and Longitude coordinates
- Accuracy and altitude information
- Bearing and speed data
- Location timestamp

## What You'll Learn

1. ✓ How to request runtime permissions for location
2. ✓ How to use FusedLocationProviderClient
3. ✓ How to retrieve current GPS location
4. ✓ How to handle location updates
5. ✓ How to display location data to users
6. ✓ How to manage location permissions correctly

## Main Activity
- **GPS.java** - Main activity with location services implementation

## How to Run

1. Open the project in Android Studio
2. Ensure device/emulator has location services enabled
3. Build and run on an emulator or physical device
4. Grant location permissions when prompted
5. View latitude, longitude, and accuracy information

## Files Structure
```
Exp7/
├── app/src/main/
│   ├── java/com/storm/exp7/
│   │   └── GPS.java
│   ├── res/layout/
│   │   └── activity_gps.xml
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Permissions Required
```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

## Related Android Concepts
- Location: `FusedLocationProviderClient`, `Location`
- Permissions: `ActivityCompat`, `PermissionChecker`
- Location Request: `LocationRequest`
- Google Play Services: Location APIs
- UI Display: `TextView` for location data
