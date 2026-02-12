# Experiment 3: ScrollView with Styled Text

## Aim

Implement an application that displays long formatted text using ScrollView and HTML/Spannable formatting.

## Objective

To understand how to handle scrollable content and format text in Android.

## Outcome

Students will be able to create scrollable layouts and apply rich text styling.

## Process Flow

1. Create a new project named "Experiment_3".
2. Modify `activity_main.xml` to include a `ScrollView`.
3. Inside the `ScrollView`, add a `TextView` with a large amount of text.
4. In `MainActivity.java`, use `Html.fromHtml()` or `SpannableString` to style the text programmatically.
5. Run the application and verify scrolling and text styling.

## Code

### `activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">

    <TextView
        android:id="@+id/styledTextView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="16sp" />

</ScrollView>
```

### `MainActivity.java`

```java
package com.csit.experiment_3;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.styledTextView);
        String htmlText = "<h1>Styled Text Example</h1>" +
                "<p>This is an example of <b>bold</b>, <i>italic</i>, and <u>underlined</u> text.</p>" +
                "<p>We can also add <font color='red'>colored text</font>.</p>" +
                "<br><br>" +
                "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ... (repeat for long text)</p>";
        
        textView.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT));
    }
}
```
