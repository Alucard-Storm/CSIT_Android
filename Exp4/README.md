# Experiment 4: Enhanced Drawing Graphics

## Overview
This experiment demonstrates **custom graphics drawing** in Android using **Canvas** and **Paint** objects. It shows how to create custom views and draw various shapes with anti-aliasing for smooth, high-quality graphics.

## Key Concepts

### 1. Custom View Creation
- Extending the View class
- Overriding the onDraw() method
- Creating custom drawing logic

### 2. Canvas and Paint
- Canvas: The drawing surface for graphics
- Paint: Defines how to draw shapes (color, style, size, etc.)
- Anti-aliasing for smooth edges

### 3. Drawing Shapes
- **Rectangle**: Using drawRect()
- **Circle**: Using drawCircle()
- **Lines**: Using drawLine()
- **Triangle**: Using Path and drawPath()
- **Text**: Using drawText() with custom styling

### 4. Graphics Properties
- Fill and stroke styles
- Color definitions
- Line width and anti-aliasing settings

## What You'll Learn

1. ✓ How to create custom View classes
2. ✓ How to use Canvas for drawing
3. ✓ How to configure Paint for different drawing styles
4. ✓ How to draw basic geometric shapes
5. ✓ How to implement anti-aliasing for quality graphics

## Main Activity
- **Graphics.java** - Main activity containing custom drawing implementation

## How to Run

1. Open the project in Android Studio
2. Build and run on an emulator or physical device
3. View the rendered graphics on screen
4. Observe the smooth edges due to anti-aliasing

## Files Structure
```
Exp4/
├── app/src/main/
│   ├── java/com/storm/exp4/
│   │   ├── Graphics.java
│   │   └── CustomGraphicsView.java
│   ├── res/layout/
│   │   └── activity_graphics.xml
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Related Android Concepts
- Custom Views: `View` class, `onDraw()`
- Graphics: `Canvas`, `Paint` classes
- Shapes: `Path`, `Rect`, `Circle`
- Color Management: `Color` class
- Anti-aliasing: `Paint.ANTI_ALIAS_FLAG`
