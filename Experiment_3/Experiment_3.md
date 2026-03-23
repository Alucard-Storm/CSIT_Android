# Experiment 3 – ScrollView with Styled Text

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [← Experiment 2](../Experiment_2/Experiment_2.md) | [Next → Experiment 4](../Experiment_4/Experiment_4.md)

> [**Notes**](./Notes_3.md)

---

## Aim
To implement an Android application that displays long formatted text using `ScrollView` with HTML and `SpannableString` formatting.

## Objective
- Use `ScrollView` for vertically scrollable content
- Apply HTML formatting using `Html.fromHtml()`
- Apply inline styles using `SpannableString` (bold, color, underline, clickable)

## Prerequisites / Theory

### ScrollView
A `ViewGroup` that allows its **single direct child** to scroll vertically. Wrap multiple child views in a `LinearLayout` first.

### Html.fromHtml()
Converts an HTML string into a `Spanned` object renderable by `TextView`.

Supported tags:
| Tag | Effect |
|---|---|
| `<b>` | Bold |
| `<i>` | Italic |
| `<u>` | Underline |
| `<font color="#hex">` | Text color |
| `<h1>–<h6>` | Headings |
| `<br>` | Line break |
| `<p>` | Paragraph |

**API 24+ requirement:** Use `Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)`.

### SpannableString
Allows applying multiple styles to **specific character ranges** in a string:
```java
SpannableString ss = new SpannableString("Hello World");
ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
ss.setSpan(new StyleSpan(Typeface.BOLD), 6, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
```

---

## Procedure
1. Create a project with a `ScrollView` containing a `LinearLayout`
2. Add two `TextView`s: one using `Html.fromHtml()`, one using `SpannableString`
3. Demonstrate styled text with clickable spans as a bonus

---

## Code

**activity_main.xml:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">

        <!-- Section 1: HTML Formatting -->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Section 1: HTML Formatting"
            android:textSize="18sp"
            android:textStyle="bold"
            android:textColor="#6200EE"
            android:layout_marginBottom="8dp"/>

        <TextView
            android:id="@+id/tvHtml"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textSize="15sp"
            android:lineSpacingExtra="4dp"
            android:layout_marginBottom="24dp"/>

        <!-- Section 2: SpannableString -->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Section 2: SpannableString"
            android:textSize="18sp"
            android:textStyle="bold"
            android:textColor="#6200EE"
            android:layout_marginBottom="8dp"/>

        <TextView
            android:id="@+id/tvSpannable"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textSize="15sp"
            android:lineSpacingExtra="4dp"
            android:layout_marginBottom="24dp"/>

        <!-- Section 3: Clickable Span -->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Section 3: Clickable Span"
            android:textSize="18sp"
            android:textStyle="bold"
            android:textColor="#6200EE"
            android:layout_marginBottom="8dp"/>

        <TextView
            android:id="@+id/tvClickable"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textSize="15sp"
            android:movementMethod="android.text.method.LinkMovementMethod"/>

    </LinearLayout>
</ScrollView>
```

**MainActivity.java:**
```java
package com.example.scrollstyle;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupHtmlSection();
        setupSpannableSection();
        setupClickableSection();
    }

    private void setupHtmlSection() {
        TextView tvHtml = findViewById(R.id.tvHtml);
        String html =
            "<h3>Android Programming – CSIT-606</h3>" +
            "<p>Android is an <b>open-source</b> operating system based on " +
            "the <i>Linux kernel</i>, developed by <u>Google</u>.</p>" +
            "<p><font color='#E91E63'>Key Components:</font><br>" +
            "• <b>Activity</b> – Screen with UI<br>" +
            "• <b>Service</b> – Background task<br>" +
            "• <b>BroadcastReceiver</b> – System events<br>" +
            "• <b>ContentProvider</b> – Shared data</p>" +
            "<p>The Activity lifecycle: " +
            "<font color='#1565C0'>onCreate → onStart → onResume → " +
            "onPause → onStop → onDestroy</font></p>";

        tvHtml.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT));
    }

    private void setupSpannableSection() {
        TextView tvSpan = findViewById(R.id.tvSpannable);
        String text = "SpannableString Demo: Bold, Italic, Color, Strikethrough, Large";
        SpannableString ss = new SpannableString(text);
        int flags = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;

        // Bold: "Bold"
        ss.setSpan(new StyleSpan(Typeface.BOLD), 22, 26, flags);
        // Italic: "Italic"
        ss.setSpan(new StyleSpan(Typeface.ITALIC), 28, 34, flags);
        // Color: "Color"
        ss.setSpan(new ForegroundColorSpan(Color.RED), 36, 41, flags);
        // Strikethrough: "Strikethrough"
        ss.setSpan(new StrikethroughSpan(), 43, 56, flags);
        // Larger size: "Large"
        ss.setSpan(new RelativeSizeSpan(1.5f), 58, 63, flags);
        // Underline: entire first word
        ss.setSpan(new UnderlineSpan(), 0, 15, flags);

        tvSpan.setText(ss);
    }

    private void setupClickableSection() {
        TextView tvClickable = findViewById(R.id.tvClickable);
        String text = "Tap here to see a Toast message from a ClickableSpan!";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(MainActivity.this,
                    "ClickableSpan works!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(true);
            }
        };

        ss.setSpan(clickSpan, 4, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // "here"
        tvClickable.setText(ss);
        tvClickable.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
```

---

## Output
A scrollable screen with three sections:
- Section 1: HTML-styled text with headings, bold, italic, underline, and color
- Section 2: `SpannableString` with bold, italic, colored, strikethrough, and larger text on specific words
- Section 3: Tapping "here" triggers a Toast via `ClickableSpan`

## Viva Questions
1. What is a `ScrollView`? How many children can it have?
2. How is `Html.fromHtml()` different from `SpannableString`?
3. What is `Spannable.SPAN_EXCLUSIVE_EXCLUSIVE`?
4. What is `ClickableSpan`? What is needed to activate it?
5. What is `RelativeSizeSpan(1.5f)`? What does `1.5f` mean?

## Result
An Android application demonstrating scrollable, multi-style text using both `Html.fromHtml()` and `SpannableString` with clickable spans was successfully implemented.
