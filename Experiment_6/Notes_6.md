# Practical 6 – Event Handling and Intents

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [Back to Experiment 6](./Experiment_6.md)

---

## What is an Intent?

An **Intent** is a messaging object in Android used to request an action from another component. It is the primary mechanism for communication between Android components — Activities, Services, and BroadcastReceivers. Intents can start an Activity, start a Service, or deliver a broadcast.

The word "intent" literally means the app's intention — what it wants to do next.

---

## Explicit Intents

An **Explicit Intent** specifies the exact component (class) to start. It is used for communication **within your own application**, where you know exactly which Activity or Service to launch.

```java
Intent intent = new Intent(this, SecondActivity.class);
intent.putExtra("username", "Akshay");
startActivity(intent);
```

Retrieving data in the target Activity:
```java
String name = getIntent().getStringExtra("username");
```

### Passing Data
The `putExtra()` method attaches key-value pairs to the Intent. Supported types include `String`, `int`, `boolean`, `float`, `Serializable`, and `Parcelable`. The receiving component reads the data using the matching `get*Extra()` method.

---

## Implicit Intents

An **Implicit Intent** does not specify a target component. Instead, it declares a general action to perform. The Android OS finds the most suitable app or component to handle the intent based on the device's installed apps and their declared Intent Filters.

### Common Actions

| Code | Result |
|---|---|
| `Intent.ACTION_VIEW` + http URI | Opens the default browser |
| `Intent.ACTION_DIAL` + tel URI | Opens the phone dialer |
| `Intent.ACTION_SEND` + text/plain | Opens the share chooser |
| `Intent.ACTION_SENDTO` + mailto URI | Opens the email client |
| `Intent.ACTION_VIEW` + geo URI | Opens Google Maps |

```java
// Open browser
Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://rgpv.ac.in"));
startActivity(intent);

// Open dialer
Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+919000000000"));
startActivity(intent);
```

### Intent Chooser
When multiple apps can handle an implicit intent, use `Intent.createChooser()` to always show the app chooser dialog rather than launching the last-used app silently:
```java
startActivity(Intent.createChooser(intent, "Share via"));
```

---

## Intent Filter

An Intent Filter in `AndroidManifest.xml` declares what kinds of implicit intents a component can respond to. When an implicit intent is fired, Android checks all installed apps' Intent Filters to find a match.

The launcher Activity must have the `ACTION_MAIN` + `CATEGORY_LAUNCHER` filter to appear in the app drawer:
```xml
<intent-filter>
    <action android:name="android.intent.action.MAIN"/>
    <category android:name="android.intent.category.LAUNCHER"/>
</intent-filter>
```

---

## Event Handling

Android uses the **listener pattern** for handling UI events. A listener is an interface with callback methods that are called when an event occurs.

Common listeners:
| Listener | Event |
|---|---|
| `OnClickListener` | Single tap |
| `OnLongClickListener` | Long press |
| `OnFocusChangeListener` | Input focus gained/lost |
| `TextWatcher` | Text changed in EditText |

```java
button.setOnClickListener(v -> {
    // handle click
});
```

---

## ActivityResultLauncher

`startActivityForResult()` was the old mechanism for starting an Activity and receiving a result back. This API was **deprecated in API 29** due to lifecycle issues and poor separation of concerns.

The modern replacement is `ActivityResultLauncher`, obtained by calling `registerForActivityResult()` before the Activity is created (in `onCreate()` or as a field). It takes a **contract** that defines the input/output types.

```java
ActivityResultLauncher<Intent> launcher = registerForActivityResult(
    new ActivityResultContracts.StartActivityForResult(),
    result -> {
        if (result.getResultCode() == RESULT_OK) {
            String data = result.getData().getStringExtra("reply");
        }
    }
);

// Launch it
launcher.launch(new Intent(this, SecondActivity.class));
```

### Returning a Result
The launched Activity calls `setResult()` before finishing:
```java
Intent result = new Intent();
result.putExtra("reply", "Done!");
setResult(RESULT_OK, result);
finish();
```

### Common Contracts
| Contract | Purpose |
|---|---|
| `StartActivityForResult` | General activity result |
| `RequestPermission` | Request a single permission |
| `CreateDocument` | SAF file creation |
| `OpenDocument` | SAF file open |
| `TakePicture` | Camera capture |
