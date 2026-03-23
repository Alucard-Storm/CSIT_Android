# CSIT-606 Android Programming – Quick Revision Notes

**RGPV VI Semester | Last-minute exam prep**

---

## Unit I – Android Basics

- Android = Open-source OS based on **Linux kernel**, by Google
- Architecture (bottom to top): **Linux Kernel → Libraries → ART → Framework → Apps**
- **ART** = Android Runtime → uses **AOT** (Ahead-of-Time) compilation → faster than old Dalvik JIT
- `.apk` = Android Package = your compiled and signed app bundle

---

## Unit II – Dev Environment

- Android Studio uses **Gradle** as build system
- Key folders: `src/main/java` (code), `src/main/res` (resources), `AndroidManifest.xml`
- `R.java` = auto-generated class mapping resource names to integer IDs
- `minSdk` = lowest Android version supported; `targetSdk` = version the app is optimized for
- `compileSdk` = SDK version used for compilation (should be latest)

---

## Unit III – Components & Lifecycle

**4 Application Components:**
Activity | Service | BroadcastReceiver | ContentProvider

**Activity Lifecycle (must memorize):**
`onCreate → onStart → onResume → [RUNNING] → onPause → onStop → onDestroy`

- `onPause()` → guaranteed to be called, save data here
- `onStop()` → release heavy resources (camera, sensors)
- Rotation = `onPause → onStop → onDestroy → onCreate` (new instance)

---

## Unit IV – Intents & UI

**Intent types:**
- **Explicit** = you name the target → `new Intent(this, Target.class)`
- **Implicit** = OS picks the app → `ACTION_VIEW`, `ACTION_SEND`

**Passing data between activities:**
- Send: `intent.putExtra("key", value)`
- Receive: `getIntent().getStringExtra("key")`

**Layout units:**
- `dp` → use for sizes, margins, padding
- `sp` → use **only** for text sizes
- `px` → avoid (not density-independent)

**ConstraintLayout** = flat hierarchy, best performance, recommended over RelativeLayout

---

## Unit V – Modern APIs

**RecyclerView vs ListView:**
| | ListView | RecyclerView |
|---|---|---|
| ViewHolder | Optional | Mandatory |
| Animation | None built-in | Built-in |
| Layout types | Vertical only | Linear, Grid, Staggered |
| Performance | Slower | Better |

**Room = 3 annotations:**
- `@Entity` → table (data class)
- `@Dao` → queries (interface)
- `@Database` → creates DB instance (abstract class)

**WorkManager = use when:**
- Work must survive app kill or device restart
- Supports constraints (network required, charging, etc.)
- `OneTimeWorkRequest` vs `PeriodicWorkRequest`

**Scoped Storage (Android 10+):**
- `getFilesDir()` = internal, private, no permission
- `getExternalFilesDir()` = external, app-specific, no permission
- SAF (`ActivityResultContracts`) = let user pick files from anywhere

---

## Old vs Modern (Exam Favourite)

| Deprecated | Replace With |
|---|---|
| `AsyncTask` | `ExecutorService` + `Handler` |
| `ListView` | `RecyclerView` |
| Raw `SQLiteOpenHelper` | **Room** |
| `AlarmManager` for bg work | **WorkManager** |
| `startActivityForResult()` | `ActivityResultLauncher` |

---

## Top 10 Viva Topics

1. Activity Lifecycle — draw and explain
2. Explicit vs Implicit Intent with example
3. What is Room? Name its 3 components
4. What is RecyclerView? Why is it better than ListView?
5. What is WorkManager? When do you use it?
6. What is Scoped Storage? Why was it introduced?
7. What is `dp` vs `sp` vs `px`?
8. What is ART? How is it different from Dalvik?
9. What is AndroidManifest.xml?
10. What is `ConstraintLayout` and why is it preferred?

---

*Quick notes for CSIT-606 | RGPV VI Semester*
