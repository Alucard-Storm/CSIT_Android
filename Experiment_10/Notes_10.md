# Practical 10 – File Handling using App-Specific Storage / SAF

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [Back to Experiment 10](./Experiment_10.md)

---

## Android Storage Overview

Android provides several locations for storing application data, each with different accessibility, persistence, and permission requirements. Choosing the right storage type is important for both correctness and user privacy.

---

## Internal Storage

Internal storage refers to the device's built-in memory, specifically the app's **private directory** at `/data/data/<package_name>/files/`. Files stored here are:
- **Private** — no other app can access them (without root access)
- **Always available** — not affected by external storage or SD card state
- **Deleted when the app is uninstalled**
- Require **no permissions** to read or write

### Access Methods

```java
// Write
FileOutputStream fos = openFileOutput("notes.txt", Context.MODE_PRIVATE);
fos.write("Hello".getBytes());
fos.close();

// Read
FileInputStream fis = openFileInput("notes.txt");
byte[] buffer = new byte[fis.available()];
fis.read(buffer);
fis.close();
String content = new String(buffer);

// Get the directory path as a File object
File dir = getFilesDir(); // → /data/data/com.example.app/files/
```

### File Write Modes
| Mode | Behaviour |
|---|---|
| `Context.MODE_PRIVATE` | Creates the file or **overwrites** it if it exists |
| `Context.MODE_APPEND` | Creates the file or **appends** to it if it exists |

---

## External App-Specific Storage

`getExternalFilesDir()` returns a directory on the device's external storage (or emulated external storage) that is specific to your app. Key properties:
- Requires **no permissions** from Android 10 (API 29) onward
- Files are **deleted when the app is uninstalled**
- Files **are visible** to the user through the system file manager
- May not be available if the SD card is removed

```java
File dir  = getExternalFilesDir(null);
File file = new File(dir, "export.txt");
```

---

## Scoped Storage

**Scoped Storage** was introduced in Android 10 (API 29) as a privacy and security improvement. Before Android 10, an app with `WRITE_EXTERNAL_STORAGE` permission could read and write **any file** anywhere on the shared external storage — including other apps' files, photos, and documents. This was a major privacy concern.

### What Changed with Scoped Storage

- Apps can only freely access their own **app-specific directories**
- Accessing **shared media** (photos, videos, music) requires using the `MediaStore` API
- Accessing **arbitrary files** (documents, downloads) requires using the **Storage Access Framework**
- The broad `WRITE_EXTERNAL_STORAGE` permission is **blocked** on Android 10+ for apps targeting API 29+

---

## Storage Access Framework (SAF)

The **Storage Access Framework** allows users to browse and select files from any location — internal storage, external storage, cloud providers like Google Drive — through a standardized **system file picker UI**. The app does not need any storage permission; the user explicitly grants access to specific files or directories.

SAF is the correct modern approach for apps that need to read or write user-chosen files.

### ActivityResultContracts for SAF

| Contract | User Action | Returns |
|---|---|---|
| `CreateDocument(mimeType)` | Chooses a name and save location for a new file | `Uri` of created file |
| `OpenDocument(mimeTypes[])` | Picks one existing file to open | `Uri` of chosen file |
| `OpenMultipleDocuments()` | Picks multiple files | `List<Uri>` |
| `OpenDocumentTree()` | Picks an entire directory | `Uri` of directory |

```java
// Register the launcher (must be done before onCreate completes)
ActivityResultLauncher<String> createLauncher = registerForActivityResult(
    new ActivityResultContracts.CreateDocument("text/plain"),
    uri -> {
        if (uri != null) {
            // Write to this URI
        }
    }
);

// Launch the file picker
createLauncher.launch("my_document.txt");
```

---

## ContentResolver

When working with SAF file URIs, you cannot use `new FileOutputStream(file)` because the file may not be on the local filesystem (it could be in cloud storage). Instead, you use `ContentResolver` to open streams.

`ContentResolver` is a system-level class that abstracts file access regardless of where the content is actually stored:

```java
// Write to a SAF URI
try (OutputStream os = getContentResolver().openOutputStream(uri)) {
    os.write("Hello SAF".getBytes());
}

// Read from a SAF URI
try (InputStream is = getContentResolver().openInputStream(uri);
     BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
    }
    String content = sb.toString();
}
```

---

## Storage Decision Guide

| Need | Solution | Permission Needed |
|---|---|---|
| Private app data, not user-visible | `getFilesDir()` (internal) | None |
| App data, user-visible in file manager | `getExternalFilesDir()` | None (API 29+) |
| User picks a file to open/save | SAF + `ActivityResultContracts` | None |
| Access photos, videos, audio | `MediaStore` API | `READ_MEDIA_*` (API 33+) |
| Full filesystem access (not recommended) | Legacy `WRITE_EXTERNAL_STORAGE` | Blocked on API 30+ |

---

## try-with-resources

All file stream operations should use **try-with-resources** (Java 7+) to automatically close streams even if an exception occurs, preventing resource leaks:

```java
try (FileOutputStream fos = openFileOutput("file.txt", MODE_PRIVATE)) {
    fos.write(data.getBytes());
} catch (IOException e) {
    e.printStackTrace();
}
// fos is automatically closed here
```
