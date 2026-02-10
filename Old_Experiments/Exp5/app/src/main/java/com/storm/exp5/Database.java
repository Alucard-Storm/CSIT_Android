package com.storm.exp5;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Experiment 6: SQLite Database Implementation
 * This activity demonstrates:
 * 1. SQLite database creation and management
 * 2. CRUD operations (Create and Read implemented)
 * 3. Using SQLiteOpenHelper for database operations
 */
public class Database extends AppCompatActivity {
    private SQLiteDatabase database;
    private EditText inputText;
    private TextView displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        // Initialize database helper and get writable database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        // Initialize UI components
        inputText = findViewById(R.id.inputText);
        displayText = findViewById(R.id.displayText);
        Button saveButton = findViewById(R.id.saveButton);
        Button displayButton = findViewById(R.id.displayButton);

        // Set click listeners for buttons
        saveButton.setOnClickListener(v -> saveData());
        displayButton.setOnClickListener(v -> displayData());
    }

    // Save data to database
    private void saveData() {
        ContentValues values = new ContentValues();
        values.put("note", inputText.getText().toString());
        database.insert("notes", null, values);
        inputText.setText("");
    }

    // Retrieve and display all notes
    @SuppressLint("Range")
    private void displayData() {
        Cursor cursor = database.query("notes", null, null, null, null, null, null);
        StringBuilder result = new StringBuilder();
        while(cursor.moveToNext()) {
            result.append(cursor.getString(cursor.getColumnIndex("note"))).append("\n");
        }
        displayText.setText(result.toString());
        cursor.close();
    }

    /**
     * SQLiteOpenHelper subclass for database operations
     */
    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Database context) {
            super(context, "NotesDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Create notes table
            db.execSQL("CREATE TABLE notes (_id INTEGER PRIMARY KEY AUTOINCREMENT, note TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }
}