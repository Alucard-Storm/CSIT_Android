# Experiment 1: ScrollView with HTML Formatting

## Overview
This experiment demonstrates the implementation of **ScrollView** widget with **HTML text formatting** in Android. It shows how to handle long content that exceeds the screen size and apply HTML styling to text.

## Key Concepts

### 1. ScrollView
- A container that allows scrolling of its child views
- Used when content exceeds the visible screen area
- Provides vertical scrolling functionality

### 2. HTML Text Formatting
- Using Android's `Html` class to format text
- Supports tags like `<b>` (bold), `<i>` (italic), `<u>` (underline), etc.
- Dynamic text styling without needing multiple TextViews

### 3. TextView with HTML
- Displaying formatted text in a TextView
- Using `Html.fromHtml()` to convert HTML strings to styled text

## What You'll Learn

1. ✓ How to implement ScrollView in XML layouts
2. ✓ How to apply HTML formatting to text dynamically
3. ✓ How to handle long content gracefully
4. ✓ Basic text styling techniques in Android

## Main Activity
- **ScrollView.java** - Main activity that demonstrates ScrollView and HTML formatting

## How to Run

1. Open the project in Android Studio
2. Build and run on an emulator or physical device
3. Scroll through the formatted text content
4. Observe the different HTML styling applied to the text

## Files Structure
```
Exp1/
├── app/src/main/
│   ├── java/com/storm/exp1/
│   │   └── ScrollView.java
│   ├── res/layout/
│   │   └── activity_scrollview.xml
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Related Android Concepts
- Android Widget: `ScrollView`
- Text Formatting: `Html` class
- Text Display: `TextView`
