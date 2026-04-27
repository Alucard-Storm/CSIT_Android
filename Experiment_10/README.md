# Experiment 10: File Handling using App-Specific Storage / SAF

## Aim

Read and write files using internal storage or Storage Access Framework with proper permissions.

## Objective

To understand file system access and data persistence on device storage.

## Outcome

Students will be able to manage files securely within the Android environment.

## Process Flow

1. For internal storage: Use `openFileOutput` and `openFileInput`.
2. For external/shared storage: Use `Intent.ACTION_CREATE_DOCUMENT` or `Intent.ACTION_OPEN_DOCUMENT`.
3. Handle runtime permissions if accessing shared storage (though SAF avoids this for user-selected files).
4. Implement File Input/Output streams to read/write data.

## Code

### `MainActivity.java`

```java
package com.csit.experiment_10;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final int WRITE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnWriteInternal = findViewById(R.id.btnWriteInternal);
        Button btnSAF = findViewById(R.id.btnSAF);

        btnWriteInternal.setOnClickListener(v -> writeToInternalStorage());
        btnSAF.setOnClickListener(v -> createFileWithSAF());
    }

    private void writeToInternalStorage() {
        String filename = "myfile";
        String fileContents = "Hello world!";
        try (FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE)) {
            fos.write(fileContents.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createFileWithSAF() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "myfile.txt");
        startActivityForResult(intent, WRITE_REQUEST_CODE);
    }
}
```

### `activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="24dp">

    <Button
        android:id="@+id/btnWriteInternal"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Write to Internal Storage"
        android:layout_marginBottom="16dp" />

    <Button
        android:id="@+id/btnSAF"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Create File with SAF" />

</LinearLayout>
```
