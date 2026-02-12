# Experiment 6: Event Handling and Intents

## Aim

Implement explicit and implicit intents to navigate between activities and launch system applications.

## Objective

To learn how to handle user events and communicate between components using Intents.

## Outcome

Students will be able to build multi-screen applications and interact with other apps.

## Process Flow

1. Create a "Main Activity" with two buttons: one for Explicit Intent, one for Implicit Intent.
2. Create a "Second Activity" to be launched explicitly.
3. In `MainActivity.java`, set `OnClickListener` for buttons.
4. For Explicit Intent: `startActivity(new Intent(this, SecondActivity.class))`.
5. For Implicit Intent: `startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com")))`.

## Code

### `MainActivity.java`

```java
package com.csit.experiment_6;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button explicitButton = findViewById(R.id.btnExplicit);
        Button implicitButton = findViewById(R.id.btnImplicit);

        explicitButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        implicitButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
            startActivity(intent);
        });
    }
}
```

### `AndroidManifest.xml`

```xml
<activity android:name=".SecondActivity"
    android:parentActivityName=".MainActivity">
    <!-- Parent activity meta-data to support 4.0 and lower -->
    <meta-data
        android:name="android.support.PARENT_ACTIVITY"
        android:value=".MainActivity" />
</activity>
```
