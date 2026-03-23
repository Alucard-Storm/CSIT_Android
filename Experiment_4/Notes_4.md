# Practical 4 – UI Components with Themes, Fonts, and Colors

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [Back to Experiment 4](./Experiment_4.md)

---

## Material Design

Material Design is Google's design system for building visual, motion, and interaction design for Android apps. It provides a set of principles, components, and guidelines that help developers create beautiful, consistent, and accessible user interfaces.

The Material Components for Android library (`com.google.android.material`) provides ready-to-use implementations such as `TextInputLayout`, `MaterialButton`, `MaterialCardView`, `BottomNavigationView`, `Snackbar`, and many more.

```groovy
implementation 'com.google.android.material:material:1.11.0'
```

---

## Theme

A **theme** is a collection of styles applied globally to an Activity or the entire application. It defines the color palette, typography, shape, and visual identity of the app. Themes are defined in `res/values/themes.xml` and applied via `android:theme` in `AndroidManifest.xml`.

Material themes follow the naming pattern `Theme.MaterialComponents.*`:
- `Theme.MaterialComponents.DayNight` — supports both light and dark mode
- `Theme.MaterialComponents.DayNight.NoActionBar` — same but hides the default ActionBar

---

## Style

A **style** is a collection of attributes applied to a **single View**. It is defined in `res/values/styles.xml` and applied via `style="@style/MyStyle"` in the layout XML. For example, you can define a style for all your primary buttons with a specific corner radius, background color, and text size.

### Theme vs Style

| | Theme | Style |
|---|---|---|
| Scope | Entire app / Activity | A single View |
| Set in | `AndroidManifest.xml` | View's XML attribute |
| Inherited by | All child Views | Only that View |
| Example | `Theme.MaterialComponents.DayNight` | `@style/Widget.MaterialComponents.Button` |

---

## Color Resources

Colors are defined in `res/values/colors.xml` as `<color>` elements with hex values:

```xml
<resources>
    <color name="colorPrimary">#6200EE</color>
    <color name="colorSecondary">#03DAC5</color>
    <color name="colorError">#B00020</color>
</resources>
```

Using named color resources instead of hardcoded hex values makes it easy to rebrand an app by changing a single file, and makes the code more readable and maintainable.

---

## TextInputLayout

`TextInputLayout` is a Material Design wrapper around `EditText`. It significantly improves the user experience of text input fields by providing:

- A **floating hint label** that moves above the input field when the user starts typing
- **Inline error messages** displayed below the input field (via `setError()`)
- **Character counter** for limiting input length
- Multiple visual styles: **Filled Box** and **Outlined Box**

```xml
<com.google.android.material.textfield.TextInputLayout
    style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
    android:hint="Email Address"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <com.google.android.material.textfield.TextInputEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="textEmailAddress"/>
</com.google.android.material.textfield.TextInputLayout>
```

Setting an error:
```java
tilEmail.setError("Enter a valid email address");  // shows error
tilEmail.setError(null);                            // clears error
```

---

## Form Validation

Android provides `android.util.Patterns` for common validation:
- `Patterns.EMAIL_ADDRESS.matcher(email).matches()` — validates email format
- `Patterns.PHONE.matcher(phone).matches()` — validates phone number format

`TextUtils.isEmpty(string)` is the recommended way to check for empty strings because it handles both `null` and empty string `""` cases, unlike `string.isEmpty()` which throws `NullPointerException` on null.

---

## MaterialCardView

`MaterialCardView` is a Material Design card component providing:
- Rounded corners (`app:cardCornerRadius`)
- Elevation shadow (`app:cardElevation`)
- Stroke border (`app:strokeColor`, `app:strokeWidth`)

It is commonly used to group related UI elements visually.
