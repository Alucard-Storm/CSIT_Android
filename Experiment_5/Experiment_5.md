# Experiment 5 – Responsive Layout Design using ConstraintLayout

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [← Experiment 4](../Experiment_4/Experiment_4.md) | [Next → Experiment 6](../Experiment_6/Experiment_6.md)

> [**Notes**](./Notes_5.md)

---

## Aim
To create a responsive UI layout using `ConstraintLayout` and demonstrate screen adaptability across different device sizes.

## Objective
- Understand ConstraintLayout constraints and chains
- Use Guidelines for percentage-based positioning
- Create a layout that adapts across phones and tablets
- Understand `0dp` (match constraints) in ConstraintLayout

## Prerequisites / Theory

### ConstraintLayout
A flexible layout manager where each view is positioned using **constraints** relative to:
- Parent (`parent`)
- Another view (by ID)
- A Guideline

Advantages over older layouts:
- **Flat hierarchy** — no nested layouts needed → faster rendering
- Supports guidelines, barriers, and chains
- Default in Android Studio for new projects

### Key Concepts

**Constraints:** Every view needs at least one horizontal and one vertical constraint.
```xml
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintStart_toStartOf="parent"
```

**0dp (match constraints):** The view fills the space between its two constraints:
```xml
android:layout_width="0dp"   <!-- fills horizontal constraints -->
```

**Guidelines:** Invisible vertical or horizontal lines positioned at a fixed dp or percentage:
```xml
<androidx.constraintlayout.widget.Guideline
    android:id="@+id/guidelineV"
    android:orientation="vertical"
    app:layout_constraintGuide_percent="0.5"/>  <!-- 50% from left -->
```

**Chains:** Groups views horizontally or vertically for even distribution:
- `spread` — equal spacing
- `spread_inside` — space between views only
- `packed` — views clustered together

**Bias:** Shifts a view along an axis (0.0 = start, 0.5 = center, 1.0 = end):
```xml
app:layout_constraintHorizontal_bias="0.3"
```

---

## Procedure
1. Design a profile card layout using `ConstraintLayout`
2. Use Guidelines to divide screen into sections
3. Use chains for button distribution
4. Demonstrate aspect-ratio constraint for an image placeholder

---

## Code

**activity_main.xml:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#F5F5F5"
    android:padding="16dp">

    <!-- Vertical guideline at 50% -->
    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/glVertical"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintGuide_percent="0.5"/>

    <!-- Horizontal guideline at 40% -->
    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/glHorizontal"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_percent="0.4"/>

    <!-- Profile picture placeholder (1:1 aspect ratio) -->
    <View
        android:id="@+id/avatarView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:background="#6200EE"
        app:layout_constraintDimensionRatio="1:1"
        app:layout_constraintWidth_percent="0.35"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintBottom_toTopOf="@id/glHorizontal"
        android:layout_margin="8dp"/>

    <!-- Name label -->
    <TextView
        android:id="@+id/tvName"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Akshay Sagar"
        android:textSize="20sp"
        android:textStyle="bold"
        android:textColor="#212121"
        app:layout_constraintTop_toTopOf="@id/avatarView"
        app:layout_constraintStart_toEndOf="@id/avatarView"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginStart="12dp"
        android:layout_marginTop="8dp"/>

    <!-- Role label -->
    <TextView
        android:id="@+id/tvRole"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Android Developer\nCSIT-606, VI Semester"
        android:textSize="14sp"
        android:textColor="#757575"
        app:layout_constraintTop_toBottomOf="@id/tvName"
        app:layout_constraintStart_toEndOf="@id/avatarView"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginStart="12dp"
        android:layout_marginTop="4dp"/>

    <!-- Divider -->
    <View
        android:id="@+id/divider"
        android:layout_width="0dp"
        android:layout_height="1dp"
        android:background="#BDBDBD"
        app:layout_constraintTop_toTopOf="@id/glHorizontal"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="8dp"/>

    <!-- Stats row: three TextViews in a horizontal chain -->
    <TextView
        android:id="@+id/tvProjects"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="12\nProjects"
        android:gravity="center"
        android:textSize="14sp"
        android:layout_marginTop="16dp"
        app:layout_constraintTop_toBottomOf="@id/divider"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toStartOf="@id/tvCommits"
        app:layout_constraintHorizontal_chainStyle="spread"/>

    <TextView
        android:id="@+id/tvCommits"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="248\nCommits"
        android:gravity="center"
        android:textSize="14sp"
        android:layout_marginTop="16dp"
        app:layout_constraintTop_toBottomOf="@id/divider"
        app:layout_constraintStart_toEndOf="@id/tvProjects"
        app:layout_constraintEnd_toStartOf="@id/tvStars"/>

    <TextView
        android:id="@+id/tvStars"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="95\nStars"
        android:gravity="center"
        android:textSize="14sp"
        android:layout_marginTop="16dp"
        app:layout_constraintTop_toBottomOf="@id/divider"
        app:layout_constraintStart_toEndOf="@id/tvCommits"
        app:layout_constraintEnd_toEndOf="parent"/>

    <!-- Two buttons in a packed chain -->
    <com.google.android.material.button.MaterialButton
        android:id="@+id/btnFollow"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Follow"
        android:layout_marginTop="32dp"
        app:layout_constraintTop_toBottomOf="@id/tvProjects"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toStartOf="@id/btnMessage"
        app:layout_constraintHorizontal_chainStyle="packed"/>

    <com.google.android.material.button.MaterialButton
        android:id="@+id/btnMessage"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Message"
        android:layout_marginTop="32dp"
        android:layout_marginStart="16dp"
        style="@style/Widget.MaterialComponents.Button.OutlinedButton"
        app:layout_constraintTop_toBottomOf="@id/tvProjects"
        app:layout_constraintStart_toEndOf="@id/btnFollow"
        app:layout_constraintEnd_toEndOf="parent"/>

    <!-- Bias demo label -->
    <TextView
        android:id="@+id/tvBiasDemo"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Bias = 0.3 (left-leaning)"
        android:textSize="13sp"
        android:textColor="#9C27B0"
        app:layout_constraintTop_toBottomOf="@id/btnFollow"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.3"
        android:layout_marginTop="24dp"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

**MainActivity.java:**
```java
package com.example.constraintdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton btnFollow  = findViewById(R.id.btnFollow);
        MaterialButton btnMessage = findViewById(R.id.btnMessage);

        btnFollow.setOnClickListener(v ->
            Toast.makeText(this, "Following!", Toast.LENGTH_SHORT).show());
        btnMessage.setOnClickListener(v ->
            Toast.makeText(this, "Opening Message...", Toast.LENGTH_SHORT).show());
    }
}
```

---

## Output
A profile card layout showing: a square avatar placeholder (1:1 aspect ratio), name and role text, a divider, three equally spaced stat labels (chain), two buttons in a packed chain, and a bias-shifted text label — all responsive to screen size changes.

## Viva Questions
1. What is ConstraintLayout? Why is it preferred over RelativeLayout?
2. What is a flat hierarchy? Why does it improve performance?
3. What does `0dp` mean in ConstraintLayout?
4. What is a Guideline? How is percentage-based guideline set?
5. What is a chain in ConstraintLayout? Name the three chain styles.
6. What is `layout_constraintHorizontal_bias`? What value centers a view?

## Result
A responsive Android profile layout using ConstraintLayout with guidelines, chains, bias, and aspect-ratio constraints was successfully implemented.
