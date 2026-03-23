# CSIT-606 Android Programming – Viva Questions

**RGPV VI Semester | Organized by Experiment**

---

## General / Theory Questions

**Q1. What is Android?**
An open-source mobile OS based on the Linux kernel, developed by Google. Used in smartphones, tablets, smart TVs, and wearables.

**Q2. What are the four main components of an Android application?**
1. **Activity** – Single screen with UI
2. **Service** – Background processing without UI
3. **BroadcastReceiver** – Responds to system broadcasts
4. **ContentProvider** – Manages shared data between apps

**Q3. What is ART? How is it different from Dalvik?**
- **Dalvik** used JIT (Just-In-Time) — code compiled at runtime
- **ART** uses AOT (Ahead-Of-Time) — compiled at install time
- ART gives faster startup and better performance, introduced in Android 5.0

**Q4. What is AndroidManifest.xml?**
App configuration file declaring all components, permissions, SDK versions, and the launcher activity.

**Q5. What is R.java?**
Auto-generated file mapping all resource names to integer IDs (e.g., `R.layout.activity_main`, `R.id.btnSubmit`).

**Q6. What is `dp` vs `sp` vs `px`?**
- `dp` — density-independent pixels → use for sizes/margins
- `sp` — scale-independent pixels → use for text sizes only
- `px` — raw pixels → avoid (not device-independent)

---

## Experiment 1 – Setup & Hello World

**Q1. What is Gradle?**
Build automation tool used by Android Studio. It compiles source, packages resources, and produces the APK.

**Q2. What is an AVD?**
Android Virtual Device — an emulator with configurable screen size, API level, and hardware to test apps without a physical device.

**Q3. What does `setContentView()` do?**
Inflates the specified XML layout and sets it as the UI of the current Activity.

**Q4. What is `adb`?**
Android Debug Bridge — a command-line tool for device communication: installing APKs, log access, shell commands.

---

## Experiment 2 – Activity Lifecycle

**Q1. Draw and explain the Activity Lifecycle.**
```
onCreate → onStart → onResume → [RUNNING]
                                    ↓
                                onPause
                                    ↓
                                onStop
                                    ↓
                               onDestroy
```
- `onResume()` — app is interactive
- `onPause()` — another activity partially overlaps; save data here
- `onStop()` — app is not visible; release camera/sensors
- `onDestroy()` — activity is being destroyed

**Q2. What happens to the lifecycle on screen rotation?**
`onPause → onStop → onDestroy → onCreate → onStart → onResume` — a new instance is created.

**Q3. How do you preserve data across rotation?**
Use `onSaveInstanceState(Bundle)` to save and `onCreate(Bundle)` to restore. Or use a `ViewModel` (recommended).

**Q4. What is `Log.d()` used for?**
Outputs debug messages to Android Logcat for monitoring app behaviour during development.

---

## Experiment 3 – ScrollView with Styled Text

**Q1. What is a ScrollView?**
A ViewGroup that allows vertical scrolling of its single child view when content exceeds screen height.

**Q2. How do you display HTML-formatted text in Android?**
```java
tv.setText(Html.fromHtml("<b>Bold</b>", Html.FROM_HTML_MODE_COMPACT));
```
`FROM_HTML_MODE_COMPACT` is required for API 24+.

**Q3. What is `SpannableString`?**
A class that allows applying multiple styles (bold, color, underline, clickable) to specific portions of a string — more powerful than HTML.

**Q4. Can ScrollView have multiple direct children?**
No. It allows only one direct child. Wrap multiple views in a `LinearLayout` first.

---

## Experiment 4 – UI Components with Themes

**Q1. What is Material Design?**
Google's design system for building visually consistent, responsive Android UIs. Uses components from `com.google.android.material` library.

**Q2. What is `TextInputLayout`?**
A Material Design wrapper around `EditText` that provides floating hint labels, error messages, and outlined/filled box styles.

**Q3. How do you apply a theme in Android?**
Set `android:theme` in `AndroidManifest.xml` or in the layout XML. Material themes: `Theme.MaterialComponents.DayNight`.

**Q4. What is `style` vs `theme` in Android?**
- `style` — applies properties to a single View
- `theme` — applies properties to all Views in an Activity or app

---

## Experiment 5 – ConstraintLayout

**Q1. What is ConstraintLayout?**
A flexible layout that positions views using constraints relative to other views or the parent. Supports **flat hierarchy** with no nesting needed.

**Q2. What is a flat hierarchy and why does it matter?**
Flat hierarchy means no nested layouts. Fewer view levels = faster rendering. Nested `LinearLayouts` slow down `measure` and `layout` passes.

**Q3. What are guidelines in ConstraintLayout?**
Invisible reference lines (horizontal or vertical) used to align multiple views to a percentage or fixed position.

**Q4. What is `0dp` in ConstraintLayout?**
Means "match constraints" — the view fills the space defined by its two constraints (equivalent to `match_parent` within constraints).

---

## Experiment 6 – Intents and Event Handling

**Q1. What is an Intent?**
A messaging object for communication between components. Can start activities, services, or deliver broadcasts.

**Q2. Difference between Explicit and Implicit Intent?**
- **Explicit** — target class is specified: `new Intent(this, SecondActivity.class)`
- **Implicit** — action is specified, OS finds the right component: `Intent.ACTION_VIEW`

**Q3. What is `putExtra()` and `getStringExtra()`?**
Used to pass and retrieve key-value data between activities via an Intent.

**Q4. What is `ActivityResultLauncher`?**
The modern replacement for `startActivityForResult()`. Uses `registerForActivityResult()` with a contract.

**Q5. What is an Intent Filter in the Manifest?**
Declares which implicit intents an activity can handle. Example: `ACTION_MAIN` + `CATEGORY_LAUNCHER` marks the app's entry point.

---

## Experiment 7 – RecyclerView

**Q1. What is RecyclerView? How is it better than ListView?**
RecyclerView recycles off-screen views via a mandatory `ViewHolder` pattern. It is more efficient, supports multiple layout managers, and has built-in item animations.

**Q2. What is the ViewHolder pattern?**
Caches `findViewById()` results in a ViewHolder object so they are not called repeatedly on every scroll event.

**Q3. What is a LayoutManager in RecyclerView?**
Determines how items are arranged:
- `LinearLayoutManager` → vertical/horizontal list
- `GridLayoutManager` → grid
- `StaggeredGridLayoutManager` → Pinterest-style staggered grid

**Q4. What is `DiffUtil`?**
A utility that calculates the difference between two lists and dispatches efficient update operations to `RecyclerView`, avoiding full `notifyDataSetChanged()`.

---

## Experiment 8 – Room Database

**Q1. What is Room? Why use it instead of raw SQLite?**
Room is an abstraction layer over SQLite providing compile-time SQL verification, type safety, and less boilerplate than `SQLiteOpenHelper`.

**Q2. What are the three main components of Room?**
1. `@Entity` — defines a database table
2. `@Dao` (Data Access Object) — interface with annotated query methods
3. `@Database` — abstract class that creates the DB instance

**Q3. What annotations are used in Room Dao?**
`@Insert`, `@Update`, `@Delete`, `@Query("SELECT ...")`.

**Q4. What is `@PrimaryKey(autoGenerate = true)`?**
Marks a field as the primary key and auto-increments its value on each insert.

**Q5. Why should Room operations not run on the main thread?**
DB operations are I/O-intensive and can block the UI. Use a background `ExecutorService` or `LiveData` to offload work.

---

## Experiment 9 – WorkManager

**Q1. What is WorkManager?**
A Jetpack library for guaranteed background work that persists across app restarts and device reboots. Uses `Worker` + `WorkRequest`.

**Q2. When should you use WorkManager vs a Thread?**
- **WorkManager** — deferrable, guaranteed work (file sync, upload, notifications)
- **Thread/Executor** — immediate, in-session background work

**Q3. What is the difference between `OneTimeWorkRequest` and `PeriodicWorkRequest`?**
- `OneTimeWorkRequest` — runs once
- `PeriodicWorkRequest` — repeats at a minimum interval of 15 minutes

**Q4. What are Constraints in WorkManager?**
Conditions that must be met before work executes:
```java
Constraints c = new Constraints.Builder()
    .setRequiredNetworkType(NetworkType.CONNECTED)
    .setRequiresCharging(true)
    .build();
```

**Q5. What is a `Worker` class?**
The class you extend and override `doWork()` in. `doWork()` runs on a background thread automatically.

---

## Experiment 10 – File Handling & SAF

**Q1. What is Scoped Storage?**
Introduced in Android 10 to restrict arbitrary file system access. Apps can only access their own app-specific directories without permissions.

**Q2. Difference between internal and external app-specific storage?**
| | Internal (`getFilesDir()`) | External (`getExternalFilesDir()`) |
|---|---|---|
| Permission needed | No | No (API 29+) |
| Removed on uninstall | Yes | Yes |
| User visible | No | Yes (via file manager) |

**Q3. What is the Storage Access Framework (SAF)?**
A framework allowing users to pick or create files through a system file picker UI, without the app needing `READ/WRITE_EXTERNAL_STORAGE` permissions.

**Q4. What is `ActivityResultContracts.CreateDocument()`?**
A built-in contract for `ActivityResultLauncher` that opens the system file picker for the user to choose a save location, returning the file URI.

**Q5. What is a `Uri` in Android file operations?**
A Uniform Resource Identifier representing a file or content location. Used with `ContentResolver` to open streams to files picked via SAF.

---

*Viva questions prepared as per RGPV CSIT-606 syllabus | VI Semester*
