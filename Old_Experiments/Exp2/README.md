# Experiment 2: GUI Components, Fonts and Colors

## Overview
This experiment demonstrates working with **GUI Components** in Android, focusing on **custom fonts** and **color manipulation**. It shows how to customize the appearance of basic UI elements like TextViews and Buttons.

## Key Concepts

### 1. GUI Components
- TextViews for displaying text
- Buttons for user interaction
- Other basic Android UI widgets

### 2. Custom Fonts
- Loading custom font files (TTF, OTF) from assets
- Applying fonts to TextViews and other text-based components
- Dynamic font styling at runtime

### 3. Color Manipulation
- Setting text colors programmatically
- Using Color class for color operations
- Background color management
- Hex color codes and Color constants

### 4. Typography
- Typeface customization
- Font styling (normal, bold, italic)
- Text size adjustments

## What You'll Learn

1. ✓ How to use custom fonts in Android applications
2. ✓ How to manipulate colors programmatically
3. ✓ How to apply different Typeface styles
4. ✓ How to combine fonts and colors for UI customization

## Main Activity
- **GuiComponents.java** - Main activity demonstrating fonts and colors

## How to Run

1. Open the project in Android Studio
2. Build and run on an emulator or physical device
3. Observe the custom fonts applied to various components
4. See how colors enhance the visual appeal

## Files Structure
```
Exp2/
├── app/src/main/
│   ├── java/com/storm/exp2/
│   │   └── GuiComponents.java
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_gui_components.xml
│   │   ├── font/
│   │   │   └── custom_fonts.ttf
│   │   └── values/
│   │       └── colors.xml
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Related Android Concepts
- Android Widget: `TextView`, `Button`
- Typography: `Typeface` class
- Colors: `Color` class and color resources
- Custom Resources: Font files in assets
