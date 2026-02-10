package com.storm.exp2;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Experiment 3: GUI Components, Fonts and Colors
 * This activity demonstrates:
 * 1. Custom font implementation
 * 2. Color manipulation in Android
 * 3. Basic GUI components (TextView and Button)
 */
public class GuiComponents extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_components);

        // Initialize UI components
        TextView customFontText = findViewById(R.id.customFontText);
        Button colorButton = findViewById(R.id.colorButton);

        // Load and apply custom font from assets folder
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/custom_font.ttf");
        customFontText.setTypeface(customFont);

        // Apply custom colors to components
        customFontText.setTextColor(Color.BLUE);
        colorButton.setBackgroundColor(Color.GREEN);
    }
}