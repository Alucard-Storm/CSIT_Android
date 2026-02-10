package com.storm.exp3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Experiment 4: Layout Managers and Event Listeners
 * This activity demonstrates:
 * 1. Different types of layouts (RelativeLayout and LinearLayout)
 * 2. Button click event handling
 * 3. Toast message display
 */
public class LayoutManagers extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_managers);

        // Initialize buttons
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        // Traditional way of setting click listener
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LayoutManagers.this,
                        "Button 1 Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        // Lambda expression way of setting click listener
        button2.setOnClickListener(v ->
                Toast.makeText(LayoutManagers.this,
                        "Button 2 Clicked!", Toast.LENGTH_SHORT).show());
    }
}