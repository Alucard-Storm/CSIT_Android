# Experiment 3: Layout Managers and Event Listeners

## Overview
This experiment demonstrates **Layout Managers** in Android, specifically **RelativeLayout** and **LinearLayout**, along with **event handling** through button click listeners and **Toast messages** for user feedback.

## Key Concepts

### 1. LinearLayout
- Arranges components in a single row or column
- Orientation can be horizontal or vertical
- Uses weight property for proportional sizing

### 2. RelativeLayout
- Positions views relative to each other or parent
- More flexible positioning than LinearLayout
- Uses alignment attributes (left, right, above, below, etc.)

### 3. Event Listeners
- OnClickListener for button clicks
- Implementing View.OnClickListener interface
- Lambda expressions for concise event handling

### 4. Toast Messages
- Short-lived notifications to user
- Display at the bottom of the screen
- Non-blocking user feedback mechanism

## What You'll Learn

1. ✓ How to create and use LinearLayout
2. ✓ How to create and use RelativeLayout
3. ✓ How to implement button click listeners
4. ✓ How to display Toast messages for feedback
5. ✓ Understanding layout hierarchy and positioning

## Main Activity
- **LayoutManagers.java** - Main activity demonstrating layouts and event listeners

## How to Run

1. Open the project in Android Studio
2. Build and run on an emulator or physical device
3. Click on various buttons
4. Observe Toast messages appearing on screen
5. Notice how buttons are positioned using different layouts

## Files Structure
```
Exp3/
├── app/src/main/
│   ├── java/com/storm/exp3/
│   │   └── LayoutManagers.java
│   ├── res/layout/
│   │   └── activity_layout_managers.xml
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Related Android Concepts
- Layouts: `LinearLayout`, `RelativeLayout`
- Event Handling: `View.OnClickListener`
- User Feedback: `Toast` class
- Layout Parameters: `LayoutParams`
