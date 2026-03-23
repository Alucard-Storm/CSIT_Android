# Practical 3 – ScrollView with Styled Text

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [Back to Experiment 3](./Experiment_3.md)

---

## What is a ScrollView?

A `ScrollView` is a special `ViewGroup` in Android that provides a **vertically scrollable container**. It is used when the content of a screen is taller than the device's visible height. The user can scroll up and down to see the full content.

An important constraint of `ScrollView` is that it can only have **one direct child view**. If you need to display multiple views inside a ScrollView, you must wrap them inside a `LinearLayout` or another ViewGroup first.

For horizontal scrolling, the equivalent class is `HorizontalScrollView`.

---

## Displaying Styled Text with Html.fromHtml()

Android's `TextView` can render a subset of HTML markup using the `Html.fromHtml()` method. This converts an HTML string into a `Spanned` object that `TextView` can display.

### Supported HTML Tags

| Tag | Effect |
|---|---|
| `<b>` | Bold text |
| `<i>` | Italic text |
| `<u>` | Underlined text |
| `<font color="#hex">` | Colored text |
| `<br>` | Line break |
| `<p>` | Paragraph |
| `<h1>` to `<h6>` | Headings |

### API Requirement
From API level 24 (Android 7.0) onward, you must pass a mode flag:
```java
Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT)
```

This approach is simple but limited to the HTML tags Android supports.

---

## SpannableString

`SpannableString` is a more powerful and flexible approach to styling text. It allows you to apply **multiple different styles to specific character ranges** within a single string. Styles are applied using `setSpan()` with a starting and ending character index.

### Common Span Types

| Span | Effect |
|---|---|
| `StyleSpan(Typeface.BOLD)` | Bold text |
| `StyleSpan(Typeface.ITALIC)` | Italic text |
| `ForegroundColorSpan(Color.RED)` | Changes text color |
| `UnderlineSpan()` | Underlines text |
| `StrikethroughSpan()` | Strikes through text |
| `RelativeSizeSpan(1.5f)` | Scales text size relative to current (1.5x = 50% larger) |
| `BackgroundColorSpan(Color)` | Highlights text with a background color |
| `ClickableSpan()` | Makes a portion of text clickable |

### Usage Example
```java
SpannableString ss = new SpannableString("Hello World");
ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 5,
    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  // "Hello" turns red
```

### SPAN_EXCLUSIVE_EXCLUSIVE
This flag means the span does **not expand** when new characters are inserted at either boundary. It is the most commonly used flag.

---

## ClickableSpan

`ClickableSpan` allows specific words or phrases within a `TextView` to behave like hyperlinks. When the user taps the spanned text, `onClick()` is called.

For this to work, you must set `LinkMovementMethod` on the `TextView`:
```java
textView.setMovementMethod(LinkMovementMethod.getInstance());
```

Without this, the clickable span is defined but taps will not be detected.

---

## Html.fromHtml() vs SpannableString

| Feature | `Html.fromHtml()` | `SpannableString` |
|---|---|---|
| Ease of use | Simple | More code required |
| Flexibility | Limited to supported HTML tags | Full control over any range |
| Clickable text | Not supported | Supported via `ClickableSpan` |
| Use case | Static styled content | Dynamic, fine-grained styling |
