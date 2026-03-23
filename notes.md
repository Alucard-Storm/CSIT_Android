# CSIT-606 Android Programming – Detailed Notes

**University:** RGPV, Bhopal | **Semester:** VI

---

## UNIT I – Introduction to Android

### 1.1 Android Architecture (Layers)
```
|  Applications           | (Settings, Camera, Browser, your app...)
|  Application Framework  | (Activity Manager, Window Manager, Content Providers...)
|  Libraries + ART        | (SQLite, WebKit, Room, Media, OpenGL...)
|  Linux Kernel           | (Drivers: Display, Camera, WiFi, Audio...)
```

- **ART (Android Runtime):** Uses AOT (Ahead-of-Time) compilation since Android 5.0. Faster launch times compared to old Dalvik JIT.
- **Dalvik VM (legacy):** Used JIT (Just-in-Time) compilation, `.dex` bytecode.

### 1.2 Why Android?
- Open source (Apache License 2.0)
- Supports **Java and Kotlin** (Kotlin is now preferred)
- Rich hardware API access (GPS, Camera, Sensors, NFC)
- 3+ billion active Android devices globally

### 1.3 Android Versions Quick Reference
| Version | API Level | Name |
|---|---|---|
| Android 8.0 | 26 | Oreo |
| Android 10 | 29 | (no codename) |
| Android 12 | 31 | Snow Cone |
| Android 14 | 34 | Upside Down Cake |

---

## UNIT II – Development Environment

### 2.1 Android Studio Setup
1. Install **JDK 17** (required for AGP 8+)
2. Download Android Studio from developer.android.com
3. Install: Android SDK, AVD Manager, Build Tools
4. Set `JAVA_HOME` environment variable

### 2.2 Project Structure
```
MyApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/   ← Source files
│   │   ├── res/
│   │   │   ├── layout/         ← XML layouts
│   │   │   ├── values/         ← strings, colors, themes
│   │   │   └── drawable/       ← Images, vector assets
│   │   └── AndroidManifest.xml
│   └── build.gradle (module)
└── build.gradle (project)
```

### 2.3 build.gradle Key Sections
```groovy
android {
    compileSdk 34
    defaultConfig {
        minSdk 26
        targetSdk 34
    }
}
dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.11.0'
    implementation 'androidx.room:room-runtime:2.6.1'
    implementation 'androidx.work:work-runtime:2.9.0'
}
```

---

## UNIT III – Activity, Lifecycle & Components

### 3.1 Four Application Components
| Component | Purpose | Example |
|---|---|---|
| **Activity** | Single screen with UI | `LoginActivity` |
| **Service** | Background processing | `MusicService` |
| **BroadcastReceiver** | Respond to system events | `BootReceiver` |
| **ContentProvider** | Share data between apps | `ContactsProvider` |

### 3.2 Activity Lifecycle
```
onCreate()
   ↓
onStart()
   ↓
onResume()  ←── App is VISIBLE and INTERACTIVE here
   ↓
onPause()   ←── Another activity partially covers this one
   ↓
onStop()    ←── App is no longer visible
   ↓
onDestroy() ←── Activity is being removed from memory
```

Key rules:
- Save important data in `onPause()` (guaranteed to be called)
- Release heavy resources (camera, sensors) in `onStop()`
- `onCreate()` receives a `Bundle` for restoring state after rotation

### 3.3 AndroidManifest.xml
```xml
<manifest package="com.example.myapp">
    <uses-permission android:name="android.permission.INTERNET"/>
    <application android:theme="@style/Theme.MyApp">
        <activity android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
    </application>
</manifest>
```

---

## UNIT IV – Intents, UI Design & Layouts

### 4.1 Intent Types
**Explicit Intent** — target component is known:
```java
Intent intent = new Intent(this, SecondActivity.class);
intent.putExtra("username", "Akshay");
startActivity(intent);
```

**Implicit Intent** — OS picks the right app:
```java
// Open browser
Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
startActivity(intent);

// Send email
Intent intent = new Intent(Intent.ACTION_SENDTO);
intent.setData(Uri.parse("mailto:someone@example.com"));
startActivity(intent);
```

### 4.2 Layout Types
| Layout | Use Case |
|---|---|
| `ConstraintLayout` | Complex, flat hierarchy (recommended) |
| `LinearLayout` | Simple rows/columns |
| `RelativeLayout` | Legacy, replaced by ConstraintLayout |
| `FrameLayout` | Overlay / fragment container |
| `ScrollView` | Vertically scrollable single-child |

### 4.3 ConstraintLayout
```xml
<Button
    android:id="@+id/btnSubmit"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintTop_toBottomOf="@id/etInput"
    android:layout_margin="16dp"
    android:text="Submit"/>
```

### 4.4 Material Design Widgets
```xml
<com.google.android.material.textfield.TextInputLayout
    style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Enter Name">
    <com.google.android.material.textfield.TextInputEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
</com.google.android.material.textfield.TextInputLayout>
```

### 4.5 Density Units
| Unit | Description | Use For |
|---|---|---|
| `dp` / `dip` | Density-independent pixels | View sizes, padding, margins |
| `sp` | Scale-independent pixels | **Text sizes only** |
| `px` | Raw screen pixels | Avoid |

---

## UNIT V – Modern Android APIs

### 5.1 RecyclerView
More efficient replacement for `ListView`:
- Uses **ViewHolder pattern** to recycle views (avoids `findViewById` on every scroll)
- Requires: `RecyclerView.Adapter` + `RecyclerView.ViewHolder` + `LayoutManager`

```java
recyclerView.setLayoutManager(new LinearLayoutManager(this));
recyclerView.setAdapter(new MyAdapter(dataList));
```

### 5.2 Room Persistence Library
**Room** is the recommended abstraction over SQLite:

| Component | Role |
|---|---|
| `@Entity` | Defines a database table (POJO class) |
| `@Dao` | Interface with SQL query methods |
| `@Database` | Creates and manages the DB instance |

```java
@Entity
public class Student {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String roll;
}

@Dao
public interface StudentDao {
    @Insert void insert(Student s);
    @Query("SELECT * FROM Student") List<Student> getAll();
    @Delete void delete(Student s);
}
```

### 5.3 WorkManager
Recommended for **guaranteed background work** (even if app is killed or device restarts):
- One-time work: `OneTimeWorkRequest`
- Periodic work: `PeriodicWorkRequest`
- Constraints: require charging, network, etc.

```java
OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
    .setInitialDelay(5, TimeUnit.SECONDS)
    .build();
WorkManager.getInstance(context).enqueue(workRequest);
```

### 5.4 File Storage (Scoped Storage)
From Android 10+, apps use **Scoped Storage**:
- **App-specific internal:** `getFilesDir()` — private, no permission needed
- **App-specific external:** `getExternalFilesDir()` — no permission needed
- **Shared storage (SAF):** Use `ActivityResultContracts` to let user pick/create files

```java
// Write to internal storage (no permission needed)
FileOutputStream fos = openFileOutput("data.txt", Context.MODE_PRIVATE);
fos.write("Hello".getBytes());
fos.close();
```

---

## Important API Comparison: Old vs Modern

| Old (Deprecated) | Modern Replacement |
|---|---|
| `AsyncTask` | `ExecutorService` + `Handler`, or Kotlin Coroutines |
| `ListView` + `ArrayAdapter` | `RecyclerView` + custom `Adapter` |
| Raw `SQLiteOpenHelper` | **Room** persistence library |
| `AlarmManager` for background work | **WorkManager** |
| `startActivityForResult()` | `ActivityResultLauncher` |
| `WRITE_EXTERNAL_STORAGE` | Scoped Storage / SAF |

---

*Notes prepared as per RGPV CSIT-606 syllabus. For lab code, refer to individual experiment files.*
