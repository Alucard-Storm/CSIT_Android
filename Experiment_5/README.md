# Experiment 5: Responsive Layout Design using ConstraintLayout

## Aim

Create responsive UI layouts using ConstraintLayout and demonstrate screen adaptability.

## Objective

To master ConstraintLayout for creating complex and responsive layouts.

## Outcome

Students will be able to build flexible UIs that adapt to different screen sizes.

## Process Flow

1. Create a new project named "Experiment_5".
2. Open `activity_main.xml` and use `ConstraintLayout` as the root.
3. Add multiple views (ImageView, TextView, Button) and set constraints relative to parent and siblings.
4. Use chains, guidelines, and bias to arrange elements.
5. Rotate the device or use different emulators to test responsiveness.

## Code

### `activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">

    <TextView
        android:id="@+id/headerText"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Responsive Header"
        android:textSize="24sp"
        android:gravity="center"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <Button
        android:id="@+id/buttonLeft"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Left"
        app:layout_constraintTop_toBottomOf="@id/headerText"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toStartOf="@id/buttonRight"
        app:layout_constraintHorizontal_weight="1"
        android:layout_marginTop="32dp" />

    <Button
        android:id="@+id/buttonRight"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Right"
        app:layout_constraintTop_toBottomOf="@id/headerText"
        app:layout_constraintStart_toEndOf="@id/buttonLeft"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_weight="1"
        android:layout_marginTop="32dp" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
