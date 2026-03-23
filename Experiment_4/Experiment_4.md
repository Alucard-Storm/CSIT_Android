# Experiment 4 – UI Components with Themes, Fonts, and Colors

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [← Experiment 3](../Experiment_3/Experiment_3.md) | [Next → Experiment 5](../Experiment_5/Experiment_5.md)

> [**Notes**](./Notes_4.md)

---

## Aim
To design a user interface using `TextView`, `Button`, and `EditText` with Material Design themes, custom styles, and colors.

## Objective
- Apply Material Design components (`TextInputLayout`, `MaterialButton`)
- Define a custom theme in `res/values/themes.xml`
- Use color resources and custom font families
- Demonstrate dynamic style changes

## Prerequisites / Theory

### Material Design
Google's design system for Android. Use via:
```groovy
implementation 'com.google.android.material:material:1.11.0'
```

### Theme vs Style
| | Theme | Style |
|---|---|---|
| Scope | Entire app / Activity | A single View |
| Set in | `AndroidManifest.xml` | View XML or code |
| Example | `Theme.MaterialComponents.DayNight` | `@style/MyButtonStyle` |

### TextInputLayout
Material wrapper around `EditText` — provides floating hint, error text, and outlined/filled box:
```xml
<com.google.android.material.textfield.TextInputLayout
    style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
    android:hint="Username">
    <com.google.android.material.textfield.TextInputEditText .../>
</com.google.android.material.textfield.TextInputLayout>
```

### Color Resources
Defined in `res/values/colors.xml`:
```xml
<color name="brand_purple">#6200EE</color>
```

---

## Procedure
1. Add Material dependency in `build.gradle`
2. Set `Theme.MaterialComponents.DayNight.NoActionBar` in Manifest
3. Design UI with `TextInputLayout`, `MaterialButton`, `TextView`
4. On button click, validate input and change UI colors dynamically

---

## Code

**res/values/colors.xml:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="colorPrimary">#6200EE</color>
    <color name="colorPrimaryVariant">#3700B3</color>
    <color name="colorOnPrimary">#FFFFFF</color>
    <color name="colorSecondary">#03DAC5</color>
    <color name="colorError">#B00020</color>
    <color name="colorSuccess">#4CAF50</color>
    <color name="colorSurface">#FFFFFF</color>
    <color name="colorOnSurface">#000000</color>
</resources>
```

**res/values/themes.xml:**
```xml
<resources>
    <style name="Theme.UIDemo" parent="Theme.MaterialComponents.DayNight.NoActionBar">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryVariant">@color/colorPrimaryVariant</item>
        <item name="colorOnPrimary">@color/colorOnPrimary</item>
        <item name="colorSecondary">@color/colorSecondary</item>
        <item name="colorError">@color/colorError</item>
    </style>
</resources>
```

**activity_main.xml:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/colorSurface">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="24dp">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="UI Components Demo"
            android:textSize="24sp"
            android:textStyle="bold"
            android:textColor="@color/colorPrimary"
            android:gravity="center"
            android:layout_marginBottom="32dp"/>

        <!-- Name field -->
        <com.google.android.material.textfield.TextInputLayout
            android:id="@+id/tilName"
            style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Full Name"
            android:layout_marginBottom="16dp">
            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/etName"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:inputType="textPersonName"/>
        </com.google.android.material.textfield.TextInputLayout>

        <!-- Email field -->
        <com.google.android.material.textfield.TextInputLayout
            android:id="@+id/tilEmail"
            style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Email Address"
            android:layout_marginBottom="24dp">
            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/etEmail"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:inputType="textEmailAddress"/>
        </com.google.android.material.textfield.TextInputLayout>

        <!-- Submit button -->
        <com.google.android.material.button.MaterialButton
            android:id="@+id/btnSubmit"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Submit"
            android:textSize="16sp"
            app:cornerRadius="8dp"
            android:layout_marginBottom="16dp"/>

        <!-- Result card -->
        <com.google.android.material.card.MaterialCardView
            android:id="@+id/resultCard"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:cardCornerRadius="12dp"
            app:cardElevation="4dp"
            android:visibility="gone">

            <TextView
                android:id="@+id/tvResult"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:padding="16dp"
                android:textSize="16sp"/>
        </com.google.android.material.card.MaterialCardView>

    </LinearLayout>
</ScrollView>
```

**MainActivity.java:**
```java
package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    TextInputLayout tilName, tilEmail;
    TextInputEditText etName, etEmail;
    MaterialButton btnSubmit;
    MaterialCardView resultCard;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tilName    = findViewById(R.id.tilName);
        tilEmail   = findViewById(R.id.tilEmail);
        etName     = findViewById(R.id.etName);
        etEmail    = findViewById(R.id.etEmail);
        btnSubmit  = findViewById(R.id.btnSubmit);
        resultCard = findViewById(R.id.resultCard);
        tvResult   = findViewById(R.id.tvResult);

        btnSubmit.setOnClickListener(v -> validateAndSubmit());
    }

    private void validateAndSubmit() {
        String name  = etName.getText() != null ? etName.getText().toString().trim() : "";
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";

        boolean valid = true;

        if (TextUtils.isEmpty(name)) {
            tilName.setError("Name is required");
            valid = false;
        } else {
            tilName.setError(null);
        }

        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Enter a valid email address");
            valid = false;
        } else {
            tilEmail.setError(null);
        }

        if (valid) {
            tvResult.setText("Hello, " + name + "!\nRegistered with: " + email);
            resultCard.setVisibility(View.VISIBLE);
        }
    }
}
```

---

## Output
A form with outlined Material text fields for name and email. On tapping Submit:
- Empty or invalid inputs show inline error messages under each field
- Valid input hides errors and shows a Material card with a greeting

## Viva Questions
1. What is Material Design? Which library provides it in Android?
2. What is `TextInputLayout`? What advantages does it provide over plain `EditText`?
3. What is the difference between a `style` and a `theme`?
4. How do you show an error message under a `TextInputLayout`?
5. What is `MaterialCardView`? What properties control its appearance?

## Result
An Android UI application using Material Design components with themed colors, form validation, and dynamic result card display was successfully implemented.
