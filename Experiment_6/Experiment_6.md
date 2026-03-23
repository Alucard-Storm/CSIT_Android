# Experiment 6 – Event Handling and Intents

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [← Experiment 5](../Experiment_5/Experiment_5.md) | [Next → Experiment 7](../Experiment_7/Experiment_7.md)

> [**Notes**](./Notes_6.md)

---

## Aim
To implement explicit and implicit intents to navigate between activities and launch system applications.

## Objective
- Navigate between two activities using Explicit Intent
- Pass data between activities using `putExtra()` and retrieve using `getIntent()`
- Launch system apps (browser, dialer, email) using Implicit Intents
- Use `ActivityResultLauncher` (modern replacement for `startActivityForResult`)

## Prerequisites / Theory

### Intent
A messaging object used to communicate between components (Activity → Activity, Activity → Service, etc.)

### Explicit Intent
Target component is directly specified:
```java
Intent intent = new Intent(this, SecondActivity.class);
intent.putExtra("key", "value");
startActivity(intent);
```

### Implicit Intent
The OS finds the right component based on action and data:
```java
Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
startActivity(intent);
```

### Common Implicit Intent Actions
| Action | Result |
|---|---|
| `ACTION_VIEW` + http URI | Opens browser |
| `ACTION_DIAL` + tel URI | Opens dialer |
| `ACTION_SENDTO` + mailto URI | Opens email app |
| `ACTION_VIEW` + geo URI | Opens Maps |
| `ACTION_SEND` + text/plain | Opens share sheet |

### ActivityResultLauncher (Modern API)
Replaces deprecated `startActivityForResult()`:
```java
ActivityResultLauncher<Intent> launcher = registerForActivityResult(
    new ActivityResultContracts.StartActivityForResult(),
    result -> {
        if (result.getResultCode() == RESULT_OK) {
            String data = result.getData().getStringExtra("result");
        }
    });
launcher.launch(intent);
```

---

## Procedure
1. Create `MainActivity` with input field and buttons for explicit + implicit intents
2. Create `SecondActivity` that receives data, modifies it, and sends a result back
3. Implement implicit intents to launch browser, dialer, and share

---

## Code

**activity_main.xml:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent" android:layout_height="match_parent"
    android:orientation="vertical" android:padding="20dp">

    <TextView android:layout_width="match_parent" android:layout_height="wrap_content"
        android:text="Intents Demo" android:textSize="22sp" android:textStyle="bold"
        android:gravity="center" android:layout_marginBottom="24dp"/>

    <com.google.android.material.textfield.TextInputLayout
        style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
        android:layout_width="match_parent" android:layout_height="wrap_content"
        android:hint="Enter a message" android:layout_marginBottom="16dp">
        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/etMessage"
            android:layout_width="match_parent" android:layout_height="wrap_content"/>
    </com.google.android.material.textfield.TextInputLayout>

    <com.google.android.material.button.MaterialButton android:id="@+id/btnExplicit"
        android:layout_width="match_parent" android:layout_height="wrap_content"
        android:text="Explicit Intent → Second Activity" android:layout_marginBottom="8dp"/>

    <com.google.android.material.button.MaterialButton android:id="@+id/btnBrowser"
        style="@style/Widget.MaterialComponents.Button.OutlinedButton"
        android:layout_width="match_parent" android:layout_height="wrap_content"
        android:text="Implicit: Open Browser" android:layout_marginBottom="8dp"/>

    <com.google.android.material.button.MaterialButton android:id="@+id/btnDial"
        style="@style/Widget.MaterialComponents.Button.OutlinedButton"
        android:layout_width="match_parent" android:layout_height="wrap_content"
        android:text="Implicit: Open Dialer" android:layout_marginBottom="8dp"/>

    <com.google.android.material.button.MaterialButton android:id="@+id/btnShare"
        style="@style/Widget.MaterialComponents.Button.OutlinedButton"
        android:layout_width="match_parent" android:layout_height="wrap_content"
        android:text="Implicit: Share Text" android:layout_marginBottom="24dp"/>

    <TextView android:id="@+id/tvResult"
        android:layout_width="match_parent" android:layout_height="wrap_content"
        android:text="Result from SecondActivity will appear here"
        android:padding="12dp" android:background="#F0F0F0" android:textSize="14sp"/>
</LinearLayout>
```

**MainActivity.java:**
```java
package com.example.intentsdemo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText etMessage;
    TextView tvResult;

    // Modern replacement for startActivityForResult
    ActivityResultLauncher<Intent> secondActivityLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                String reply = result.getData().getStringExtra("reply");
                tvResult.setText("Reply from SecondActivity:\n" + reply);
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMessage = findViewById(R.id.etMessage);
        tvResult  = findViewById(R.id.tvResult);

        MaterialButton btnExplicit = findViewById(R.id.btnExplicit);
        MaterialButton btnBrowser  = findViewById(R.id.btnBrowser);
        MaterialButton btnDial     = findViewById(R.id.btnDial);
        MaterialButton btnShare    = findViewById(R.id.btnShare);

        // Explicit Intent
        btnExplicit.setOnClickListener(v -> {
            String msg = etMessage.getText() != null
                ? etMessage.getText().toString() : "";
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("message", msg.isEmpty() ? "Hello!" : msg);
            secondActivityLauncher.launch(intent);
        });

        // Implicit: Browser
        btnBrowser.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.rgpv.ac.in"));
            startActivity(intent);
        });

        // Implicit: Dialer
        btnDial.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:+910000000000"));
            startActivity(intent);
        });

        // Implicit: Share
        btnShare.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Learning Android at RGPV!");
            startActivity(Intent.createChooser(intent, "Share via"));
        });
    }
}
```

**SecondActivity.java:**
```java
package com.example.intentsdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        String received = getIntent().getStringExtra("message");
        TextView tvReceived = findViewById(R.id.tvReceived);
        tvReceived.setText("Received: " + received);

        MaterialButton btnSendBack = findViewById(R.id.btnSendBack);
        btnSendBack.setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra("reply", "Message '" + received + "' received and processed!");
            setResult(RESULT_OK, result);
            finish();
        });
    }
}
```

**activity_second.xml:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent" android:layout_height="match_parent"
    android:orientation="vertical" android:padding="24dp" android:gravity="center">

    <TextView android:text="Second Activity" android:textSize="22sp"
        android:textStyle="bold" android:layout_width="wrap_content"
        android:layout_height="wrap_content" android:layout_marginBottom="24dp"/>

    <TextView android:id="@+id/tvReceived"
        android:layout_width="wrap_content" android:layout_height="wrap_content"
        android:textSize="16sp" android:layout_marginBottom="24dp"/>

    <com.google.android.material.button.MaterialButton android:id="@+id/btnSendBack"
        android:layout_width="wrap_content" android:layout_height="wrap_content"
        android:text="Send Result Back"/>
</LinearLayout>
```

---

## Output
Main screen with input field and 4 buttons. Explicit intent opens SecondActivity with the typed message; tapping "Send Result Back" returns a reply shown in `tvResult`. Browser, dialer, and share buttons launch respective system apps.

## Viva Questions
1. What is the difference between Explicit and Implicit Intent?
2. What is `putExtra()` and `getStringExtra()`?
3. What is `ActivityResultLauncher`? Why was `startActivityForResult()` deprecated?
4. What is `Intent.createChooser()`?
5. What is `setResult(RESULT_OK, intent)` used for?
6. What is an Intent Filter in the Manifest?

## Result
An Android application demonstrating explicit activity navigation with result passing and multiple implicit intents (browser, dialer, share) was successfully implemented.
