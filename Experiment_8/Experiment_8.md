# Experiment 8 – Local Database Application using Room

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [← Experiment 7](../Experiment_7/Experiment_7.md) | [Next → Experiment 9](../Experiment_9/Experiment_9.md)

> [**Notes**](./Notes_8.md)

---

## Aim
To implement CRUD operations using the **Room** persistence library for structured local data storage.

## Objective
- Define a Room `@Entity`, `@Dao`, and `@Database`
- Perform Insert, Read, Update, Delete operations
- Run database operations on a background thread using `ExecutorService`
- Display results in a `RecyclerView`

## Prerequisites / Theory

### Why Room over raw SQLite?
| Feature | SQLiteOpenHelper | Room |
|---|---|---|
| SQL verification | At runtime | At **compile time** |
| Boilerplate | High | Low |
| LiveData support | No | Yes |
| Migration support | Manual | Built-in helpers |

### Room Architecture
```
Activity / ViewModel
        ↓
      @Dao  (interface with annotated queries)
        ↓
   @Database  (abstract class, singleton)
        ↓
    SQLite file on device
```

### Key Annotations
| Annotation | Applied To | Purpose |
|---|---|---|
| `@Entity` | Class | Maps class to a DB table |
| `@PrimaryKey` | Field | Marks primary key |
| `@ColumnInfo` | Field | Custom column name |
| `@Dao` | Interface | Marks query interface |
| `@Insert` | Method | INSERT query |
| `@Update` | Method | UPDATE query |
| `@Delete` | Method | DELETE query |
| `@Query` | Method | Custom SQL query |
| `@Database` | Abstract class | Creates DB instance |

---

## Procedure
1. Add Room dependencies to `build.gradle`
2. Create `Note.java` entity
3. Create `NoteDao.java` interface
4. Create `NoteDatabase.java` abstract class
5. Build a notes UI with add, view, and delete

---

## Code

**build.gradle (module) — dependencies:**
```groovy
implementation 'androidx.room:room-runtime:2.6.1'
annotationProcessor 'androidx.room:room-compiler:2.6.1'
```

**Note.java (Entity):**
```java
package com.example.roomdemo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "content")
    public String content;

    public Note(String title, String content) {
        this.title   = title;
        this.content = content;
    }
}
```

**NoteDao.java (DAO):**
```java
package com.example.roomdemo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Note> getAllNotes();

    @Query("SELECT * FROM notes WHERE id = :noteId")
    Note getNoteById(int noteId);

    @Query("DELETE FROM notes")
    void deleteAll();
}
```

**NoteDatabase.java (Database):**
```java
package com.example.roomdemo;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static volatile NoteDatabase INSTANCE;

    public static NoteDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (NoteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        NoteDatabase.class,
                        "note_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
```

**NoteAdapter.java:**
```java
package com.example.roomdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes;
    private final OnDeleteListener listener;

    public interface OnDeleteListener {
        void onDelete(Note note);
    }

    public NoteAdapter(List<Note> notes, OnDeleteListener listener) {
        this.notes    = notes;
        this.listener = listener;
    }

    public void updateNotes(List<Note> newNotes) {
        this.notes = newNotes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.tvTitle.setText(note.title);
        holder.tvContent.setText(note.content);
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(note));
    }

    @Override
    public int getItemCount() { return notes.size(); }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent;
        MaterialButton btnDelete;
        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle   = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
```

**res/layout/item_note.xml:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<com.google.android.material.card.MaterialCardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp"
    app:cardCornerRadius="8dp" app:cardElevation="3dp">
    <LinearLayout android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical" android:padding="12dp">
        <TextView android:id="@+id/tvTitle"
            android:layout_width="match_parent" android:layout_height="wrap_content"
            android:textStyle="bold" android:textSize="16sp" android:textColor="#212121"/>
        <TextView android:id="@+id/tvContent"
            android:layout_width="match_parent" android:layout_height="wrap_content"
            android:textSize="13sp" android:textColor="#757575" android:layout_marginTop="4dp"/>
        <com.google.android.material.button.MaterialButton android:id="@+id/btnDelete"
            style="@style/Widget.MaterialComponents.Button.TextButton"
            android:layout_width="wrap_content" android:layout_height="wrap_content"
            android:text="Delete" android:textColor="#B00020"
            android:layout_gravity="end"/>
    </LinearLayout>
</com.google.android.material.card.MaterialCardView>
```

**MainActivity.java:**
```java
package com.example.roomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    NoteDatabase db;
    NoteAdapter adapter;
    ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = NoteDatabase.getInstance(this);

        RecyclerView rv = findViewById(R.id.recyclerView);
        adapter = new NoteAdapter(new ArrayList<>(), note -> deleteNote(note));
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        TextInputEditText etTitle   = findViewById(R.id.etTitle);
        TextInputEditText etContent = findViewById(R.id.etContent);
        MaterialButton btnSave      = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String title   = etTitle.getText() != null ? etTitle.getText().toString().trim() : "";
            String content = etContent.getText() != null ? etContent.getText().toString().trim() : "";
            if (title.isEmpty()) {
                Toast.makeText(this, "Title required!", Toast.LENGTH_SHORT).show();
                return;
            }
            executor.execute(() -> {
                db.noteDao().insert(new Note(title, content));
                runOnUiThread(() -> {
                    etTitle.setText("");
                    etContent.setText("");
                    refreshList();
                });
            });
        });

        refreshList();
    }

    private void refreshList() {
        executor.execute(() -> {
            java.util.List<Note> notes = db.noteDao().getAllNotes();
            runOnUiThread(() -> adapter.updateNotes(notes));
        });
    }

    private void deleteNote(Note note) {
        executor.execute(() -> {
            db.noteDao().delete(note);
            runOnUiThread(this::refreshList);
        });
    }
}
```

---

## Output
A notes app with title and content input fields. Saved notes appear as cards in a RecyclerView. Tapping Delete removes the note from both the UI and the Room database. Data persists across app restarts.

## Viva Questions
1. What is Room? Why is it preferred over raw `SQLiteOpenHelper`?
2. What are the three components of Room? Explain each.
3. What annotations are used in a Room DAO?
4. Why must Room queries not run on the main thread?
5. What is `@PrimaryKey(autoGenerate = true)`?
6. What is a `singleton pattern` and why is it used in `NoteDatabase.getInstance()`?

## Result
An Android notes application with full CRUD operations using Room persistence library, background threading via `ExecutorService`, and RecyclerView display was successfully implemented.
