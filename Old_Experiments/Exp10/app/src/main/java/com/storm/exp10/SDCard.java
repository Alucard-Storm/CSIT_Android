package com.storm.exp10;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.*;

/**
 * Experiment 11: Writing Data to SD Card
 * This activity demonstrates:
 * 1. External storage permissions handling
 * 2. File operations on SD card
 * 3. Reading and writing text files
 * 4. Runtime permission management
 * 5. Error handling for file operations
 */
public class SDCard extends AppCompatActivity {
    // UI Components
    private EditText inputText;
    private TextView outputText;

    // Constants for permission handling and file operations
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String FILE_NAME = "sample.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdcard);

        // Initialize UI components
        inputText = findViewById(R.id.inputText);
        outputText = findViewById(R.id.outputText);
        Button writeButton = findViewById(R.id.writeButton);
        Button readButton = findViewById(R.id.readButton);

        // Set click listeners for buttons
        // Write button checks permission before writing to file
        writeButton.setOnClickListener(v -> checkPermissionAndWrite());
        // Read button checks permission before reading from file
        readButton.setOnClickListener(v -> checkPermissionAndRead());
    }

    /**
     * Checks permission before writing to file
     * If permission not granted, requests it from user
     */
    private void checkPermissionAndWrite() {
        if (checkPermission()) {
            writeToFile();
        } else {
            requestPermission();
        }
    }

    /**
     * Checks permission before reading from file
     * If permission not granted, requests it from user
     */
    private void checkPermissionAndRead() {
        if (checkPermission()) {
            readFromFile();
        } else {
            requestPermission();
        }
    }

    /**
     * Checks if the app has permission to write to external storage
     * @return true if permission is granted, false otherwise
     */
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Requests permission from user to write to external storage
     * Result is handled in onRequestPermissionsResult
     */
    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    /**
     * Writes the text from input field to a file on external storage
     * Handles various error cases and shows appropriate messages
     */
    private void writeToFile() {
        // Check if external storage is available
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            Toast.makeText(this, "External Storage not available", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get reference to file in app's external files directory
        File file = new File(getExternalFilesDir(null), FILE_NAME);
        try {
            // Write text to file
            FileWriter writer = new FileWriter(file);
            writer.write(inputText.getText().toString());
            writer.close();

            // Clear input and show success message
            Toast.makeText(this, "Data written to SD card", Toast.LENGTH_SHORT).show();
            inputText.setText("");
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error writing to file", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Reads text from file and displays it in the output TextView
     * Handles file reading errors and shows appropriate messages
     */
    private void readFromFile() {
        File file = new File(getExternalFilesDir(null), FILE_NAME);
        StringBuilder text = new StringBuilder();

        try {
            // Read file line by line
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();

            // Display read text in TextView
            outputText.setText(text.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handles the result of permission request
     * Shows appropriate message based on whether permission was granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}