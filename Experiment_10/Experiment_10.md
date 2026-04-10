# Experiment 10 – File Handling using App-Specific Storage / SAF

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [← Experiment 9](../Experiment_9/Experiment_9.md)

> [**Notes**](./Notes_10.md)

---

## Aim
To read and write files using internal app-specific storage and the Storage Access Framework (SAF) with proper permissions.

## Objective
- Write and read text files using `openFileOutput()` / `openFileInput()` (internal storage)
- Use `ActivityResultContracts` to create/open files via SAF (no storage permission needed)
- Understand Scoped Storage introduced in Android 10

## Prerequisites / Theory

### Android Storage Types
| Type | API | Permission | Survives Uninstall |
|---|---|---|---|
| Internal private | `getFilesDir()` | None | No |
| External app-specific | `getExternalFilesDir()` | None (API 29+) | No |
| Shared storage (SAF) | `ContentResolver` | None (SAF) | Yes |
| Legacy external | `Environment.getExternalStorageDirectory()` | Yes (blocked API 30+) | Yes |

### Scoped Storage (Android 10+)
Introduced to improve privacy. Apps can no longer access the entire file system. Options:
1. **Internal storage** — always available, fully private
2. **App-specific external** — `getExternalFilesDir()`, no permission needed
3. **SAF (Storage Access Framework)** — user picks location via system picker

### Storage Access Framework (SAF)
User-controlled file access via a system file picker UI:
```java
// Create a file
ActivityResultLauncher<String> createFileLauncher =
    registerForActivityResult(new ActivityResultContracts.CreateDocument("text/plain"),
        uri -> { /* write to uri via ContentResolver */ });

// Open a file
ActivityResultLauncher<String[]> openFileLauncher =
    registerForActivityResult(new ActivityResultContracts.OpenDocument(),
        uri -> { /* read from uri via ContentResolver */ });
```

---

## Procedure
1. Build a UI with text input and 4 buttons: Internal Write, Internal Read, SAF Create, SAF Open
2. Implement internal storage read/write using `openFileOutput()` / `openFileInput()`
3. Implement SAF create/open using `ActivityResultContracts`

---

## Code

**activity_main.xml:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent" android:layout_height="match_parent"
    android:orientation="vertical" android:padding="16dp">

    <TextView android:text="File Handling Demo" android:textSize="20sp"
        android:textStyle="bold" android:gravity="center"
        android:layout_width="match_parent" android:layout_height="wrap_content"
        android:layout_marginBottom="16dp"/>

    <com.google.android.material.textfield.TextInputLayout
        style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
        android:layout_width="match_parent" android:layout_height="wrap_content"
        android:hint="Enter text to save" android:layout_marginBottom="12dp">
        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/etContent"
            android:layout_width="match_parent" android:layout_height="wrap_content"
            android:minLines="3" android:gravity="top"/>
    </com.google.android.material.textfield.TextInputLayout>

    <!-- Internal storage buttons -->
    <TextView android:text="Internal Storage" android:textStyle="bold"
        android:textColor="#6200EE"
        android:layout_width="wrap_content" android:layout_height="wrap_content"
        android:layout_marginBottom="4dp"/>

    <LinearLayout android:layout_width="match_parent"
        android:layout_height="wrap_content" android:orientation="horizontal"
        android:layout_marginBottom="16dp">
        <com.google.android.material.button.MaterialButton android:id="@+id/btnWriteInternal"
            android:layout_weight="1" android:layout_width="0dp"
            android:layout_height="wrap_content" android:text="Write"
            android:layout_marginEnd="8dp"/>
        <com.google.android.material.button.MaterialButton android:id="@+id/btnReadInternal"
            style="@style/Widget.MaterialComponents.Button.OutlinedButton"
            android:layout_weight="1" android:layout_width="0dp"
            android:layout_height="wrap_content" android:text="Read"/>
    </LinearLayout>

    <!-- SAF buttons -->
    <TextView android:text="Storage Access Framework (SAF)" android:textStyle="bold"
        android:textColor="#6200EE"
        android:layout_width="wrap_content" android:layout_height="wrap_content"
        android:layout_marginBottom="4dp"/>

    <LinearLayout android:layout_width="match_parent"
        android:layout_height="wrap_content" android:orientation="horizontal"
        android:layout_marginBottom="16dp">
        <com.google.android.material.button.MaterialButton android:id="@+id/btnSafCreate"
            android:layout_weight="1" android:layout_width="0dp"
            android:layout_height="wrap_content" android:text="Save File"
            android:layout_marginEnd="8dp"/>
        <com.google.android.material.button.MaterialButton android:id="@+id/btnSafOpen"
            style="@style/Widget.MaterialComponents.Button.OutlinedButton"
            android:layout_weight="1" android:layout_width="0dp"
            android:layout_height="wrap_content" android:text="Open File"/>
    </LinearLayout>

    <!-- Output area -->
    <ScrollView android:layout_width="match_parent" android:layout_height="0dp"
        android:layout_weight="1">
        <TextView android:id="@+id/tvOutput"
            android:layout_width="match_parent" android:layout_height="wrap_content"
            android:text="Output will appear here"
            android:padding="12dp" android:background="#F5F5F5"
            android:textSize="14sp" android:fontFamily="monospace"/>
    </ScrollView>
</LinearLayout>
```

**MainActivity.java:**
```java
package com.example.filestoragedemo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    static final String INTERNAL_FILE = "my_notes.txt";
    TextInputEditText etContent;
    TextView tvOutput;

    // SAF: Create a new file
    ActivityResultLauncher<String> safCreateLauncher = registerForActivityResult(
        new ActivityResultContracts.CreateDocument("text/plain"),
        uri -> {
            if (uri != null) writeToUri(uri);
        }
    );

    // SAF: Open an existing file
    ActivityResultLauncher<String[]> safOpenLauncher = registerForActivityResult(
        new ActivityResultContracts.OpenDocument(),
        uri -> {
            if (uri != null) readFromUri(uri);
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etContent = findViewById(R.id.etContent);
        tvOutput  = findViewById(R.id.tvOutput);

        MaterialButton btnWriteInternal = findViewById(R.id.btnWriteInternal);
        MaterialButton btnReadInternal  = findViewById(R.id.btnReadInternal);
        MaterialButton btnSafCreate     = findViewById(R.id.btnSafCreate);
        MaterialButton btnSafOpen       = findViewById(R.id.btnSafOpen);

        // Internal storage write
        btnWriteInternal.setOnClickListener(v -> {
            String text = getInputText();
            try (FileOutputStream fos = openFileOutput(INTERNAL_FILE, MODE_PRIVATE)) {
                fos.write(text.getBytes());
                showOutput("Written to internal storage:\n" + getFilesDir() + "/" + INTERNAL_FILE);
                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                showOutput("Write error: " + e.getMessage());
            }
        });

        // Internal storage read
        btnReadInternal.setOnClickListener(v -> {
            try (FileInputStream fis = openFileInput(INTERNAL_FILE)) {
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                showOutput("Read from internal storage:\n\n" + new String(buffer));
            } catch (IOException e) {
                showOutput("Read error (file may not exist yet): " + e.getMessage());
            }
        });

        // SAF: Create file
        btnSafCreate.setOnClickListener(v ->
            safCreateLauncher.launch("my_document.txt")
        );

        // SAF: Open file
        btnSafOpen.setOnClickListener(v ->
            safOpenLauncher.launch(new String[]{"text/plain"})
        );
    }

    private void writeToUri(Uri uri) {
        String text = getInputText();
        try (OutputStream os = getContentResolver().openOutputStream(uri)) {
            if (os != null) {
                os.write(text.getBytes());
                showOutput("Written via SAF to:\n" + uri.toString());
                Toast.makeText(this, "File saved via SAF!", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            showOutput("SAF write error: " + e.getMessage());
        }
    }

    private void readFromUri(Uri uri) {
        try {
            InputStream is = getContentResolver().openInputStream(uri);
            if (is == null) {
                showOutput("SAF read error: Cannot open file");
                return;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line).append("\n");
            showOutput("Read via SAF:\n\n" + sb.toString());
            reader.close();
            is.close();
        } catch (IOException e) {
            showOutput("SAF read error: " + e.getMessage());
        }
    }

    private String getInputText() {
        String text = etContent.getText() != null
            ? etContent.getText().toString() : "";
        return text.isEmpty() ? "Default content from Android app." : text;
    }

    private void showOutput(String text) {
        tvOutput.setText(text);
    }
}
```

---

## Output
App with four buttons. Internal Write saves text to `getFilesDir()/my_notes.txt`, Internal Read loads it back. SAF Save opens the system file picker for the user to choose a save location. SAF Open opens a file picker to read any `.txt` file. All operations display results in the output area.

## Viva Questions
1. What is Scoped Storage? Why was it introduced in Android 10?
2. What is the difference between internal storage and app-specific external storage?
3. What is the Storage Access Framework (SAF)?
4. What is `ActivityResultContracts.CreateDocument()`?
5. How does `ContentResolver.openOutputStream(uri)` work?
6. What is `MODE_PRIVATE` in `openFileOutput()`?
7. Does SAF require any `<uses-permission>` declaration?

## Result
An Android file handling application demonstrating both internal private storage (read/write) and SAF-based file creation/reading using `ActivityResultContracts` without any storage permissions was successfully implemented.
