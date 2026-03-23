# Practical 5 – Responsive Layout Design using ConstraintLayout

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [Back to Experiment 5](./Experiment_5.md)

---

## What is ConstraintLayout?

`ConstraintLayout` is a flexible and powerful layout manager that allows you to position and size widgets in a **flat view hierarchy** — meaning no nested layouts are required. Every view in a ConstraintLayout is positioned by defining **constraints** to other views, guidelines, or the parent container.

It is the default layout in Android Studio for new projects and is recommended by Google for most UI designs.

---

## Why ConstraintLayout over RelativeLayout?

`RelativeLayout` was the predecessor to ConstraintLayout. While both allow relative positioning, ConstraintLayout is significantly superior:
- It performs **two layout passes** at most, compared to RelativeLayout's potential for multiple passes in nested scenarios
- It supports chains, guidelines, barriers, and aspect ratios — features not available in RelativeLayout
- It works better with the **Layout Editor** in Android Studio, enabling full drag-and-drop visual design

### Flat Hierarchy
A flat hierarchy means all views are direct children of the root layout with no nesting. Nested layouts cause Android to perform extra `measure` and `layout` passes for each nesting level, slowing down rendering. ConstraintLayout allows expressing complex positioning rules without any nesting.

---

## Constraints

Every view in a ConstraintLayout must have **at least one horizontal and one vertical constraint** to be properly positioned. A constraint connects an edge of one view to the edge of another view or to the parent.

```xml
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintBottom_toBottomOf="parent"
```

The above four constraints center a view both horizontally and vertically.

`app:layout_constraintTop_toBottomOf="@id/tvTitle"` means "the top of this view is connected to the bottom of `tvTitle`."

---

## 0dp (Match Constraints)

Setting `android:layout_width="0dp"` or `android:layout_height="0dp"` in a ConstraintLayout means **match constraints** — the view will expand to fill the space defined by its two constraints on that axis. This is the ConstraintLayout equivalent of `match_parent` within a bounded region.

**Example:** A button that fills the horizontal space between two guidelines:
```xml
android:layout_width="0dp"
app:layout_constraintStart_toEndOf="@id/glLeft"
app:layout_constraintEnd_toStartOf="@id/glRight"
```

---

## Guidelines

Guidelines are **invisible helper lines** that you can define at a fixed dp offset or a **percentage** of the parent's dimension. Views can be constrained to a Guideline just like any other view. They are extremely useful for creating consistent layouts across screen sizes without hardcoding absolute positions.

```xml
<androidx.constraintlayout.widget.Guideline
    android:id="@+id/glVertical"
    android:orientation="vertical"
    app:layout_constraintGuide_percent="0.5"/>
```

This creates a vertical line at exactly 50% of the screen width.

---

## Chains

A chain is a group of views linked to each other by **bidirectional constraints** on the same axis. Chains allow you to distribute multiple views along an axis.

There are three chain styles (set on the first element in the chain):

| Style | Behaviour |
|---|---|
| `spread` (default) | Views are spread evenly with equal space between all, including the edges |
| `spread_inside` | First and last views are at the edges; remaining space distributed between views |
| `packed` | Views are packed together in the center of the available space |

```xml
app:layout_constraintHorizontal_chainStyle="packed"
```

---

## Bias

Bias determines how a view is positioned along an axis when it has constraints on **both sides**. It ranges from 0.0 to 1.0:
- `0.0` — fully at the start (left / top)
- `0.5` — centered (default)
- `1.0` — fully at the end (right / bottom)
- `0.3` — shifted 30% from the start

```xml
app:layout_constraintHorizontal_bias="0.3"
```

---

## Aspect Ratio Constraint

ConstraintLayout can enforce a fixed aspect ratio on a view. Setting one dimension to `0dp` and providing a ratio ensures the view always maintains the specified width-to-height proportion, regardless of screen size.

```xml
android:layout_width="0dp"
app:layout_constraintDimensionRatio="1:1"
app:layout_constraintWidth_percent="0.4"
```

This creates a view that is always a perfect square, occupying 40% of the parent's width.

---

## Density Units

| Unit | Description | Recommended For |
|---|---|---|
| `dp` / `dip` | Density-independent pixels (160dp = 1 inch on any screen) | All sizes, margins, padding |
| `sp` | Scale-independent pixels — also accounts for user's font size preference | Text sizes **only** |
| `px` | Raw screen pixels — varies by screen density | Avoid |

Formula: `px = dp × (screen_dpi / 160)`

Always use `dp` for layout dimensions and `sp` for text sizes to ensure your UI scales correctly across all device types.
