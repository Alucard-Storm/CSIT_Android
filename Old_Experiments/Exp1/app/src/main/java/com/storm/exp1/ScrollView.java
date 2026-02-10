package com.storm.exp1;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Experiment 2: ScrollView with HTML Formatting
 * This activity demonstrates:
 * 1. Implementation of ScrollView for long content
 * 2. HTML text formatting in Android
 * 3. Basic text styling using HTML tags
 */
public class ScrollView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);

        // Find the TextView in our layout
        TextView textView = findViewById(R.id.htmlTextView);

        // Create HTML formatted text with different styling
        String htmlText = "<h1>Welcome to Android</h1>" +
                "<p>This is a <b>bold</b> text</p>" +
                "<p>This is an <i>italic</i> text</p>" +
                "<p>This is a <u>underlined</u> text</p>" +
                "<h2>Features:</h2>" +
                "<ul>" +
                "<li>HTML Formatting</li>" +
                "<li>ScrollView Implementation</li>" +
                "<li>Text Styling</li>" +
                "</ul>";

        // Set the HTML text to TextView using Html.fromHtml
        textView.setText(Html.fromHtml(htmlText));
    }
}