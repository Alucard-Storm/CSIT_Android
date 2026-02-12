# Experiment 4: UI Components with Themes, Fonts, and Colors

## Aim

Design a user interface using TextView, Button, and EditText with Material themes and styles.

## Objective

To learn how to use basic UI components and apply material design themes.

## Outcome

Students will be able to design consistent and visually appealing user interfaces.

## Process Flow

1. Create a new project named "Experiment_4".
2. Update `colors.xml` and `themes.xml` to define a custom color palette.
3. In `activity_main.xml`, add `TextView`, `EditText`, and `Button` components.
4. Apply styles and themes to these components.
5. Run the application to inspect the UI design.

## Code

### `activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="24dp"
    android:gravity="center">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Login"
        android:textSize="24sp"
        android:textStyle="bold"
        android:layout_marginBottom="32dp"/>

    <com.google.android.material.textfield.TextInputLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Username">

        <com.google.android.material.textfield.TextInputEditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>
    </com.google.android.material.textfield.TextInputLayout>

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Submit"
        android:layout_marginTop="16dp"/>

</LinearLayout>
```
