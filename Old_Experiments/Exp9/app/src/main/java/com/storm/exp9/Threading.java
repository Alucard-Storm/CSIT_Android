package com.storm.exp9;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Experiment 10: Multi-threading
 * This activity demonstrates:
 * 1. Background thread creation
 * 2. UI updates from background thread
 * 3. Progress tracking with ProgressBar
 */
public class Threading extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView statusText;
    private Button startButton;
    private Handler handler;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threading);

        // Initialize UI components
        progressBar = findViewById(R.id.progressBar);
        statusText = findViewById(R.id.statusText);
        startButton = findViewById(R.id.startButton);
        handler = new Handler(Looper.getMainLooper());

        // Set click listener for start button
        startButton.setOnClickListener(v -> {
            if (!isRunning) {
                startLongTask();
            }
        });
    }

    // Start a long-running task in background thread
    private void startLongTask() {
        isRunning = true;
        startButton.setEnabled(false);

        new Thread(() -> {
            // Simulate long task with progress updates
            for (int i = 0; i <= 100; i++) {
                final int progress = i;
                handler.post(() -> {
                    progressBar.setProgress(progress);
                    statusText.setText("Progress: " + progress + "%");
                });

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Update UI when task is complete
            handler.post(() -> {
                startButton.setEnabled(true);
                statusText.setText("Task Completed!");
                isRunning = false;
            });
        }).start();
    }
}