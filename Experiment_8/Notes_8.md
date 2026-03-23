# Practical 8 – Local Database Application using Room

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [Back to Experiment 8](./Experiment_8.md)

---

## What is Room?

**Room** is a Jetpack persistence library that provides an **abstraction layer over Android's built-in SQLite database**. It simplifies database interactions by reducing boilerplate code and providing **compile-time verification of SQL queries** — meaning if your SQL syntax is wrong, you get a build error rather than a runtime crash.

Room is the officially recommended approach for local data persistence in Android.

---

## Why Room over Raw SQLiteOpenHelper?

Raw `SQLiteOpenHelper` requires developers to:
- Write repetitive boilerplate (Cursor management, ContentValues setup, manual column index tracking)
- Discover SQL errors only at runtime (a crash in production)
- Manually map query results row-by-row to Java objects

Room solves all of these problems:

| Feature | SQLiteOpenHelper | Room |
|---|---|---|
| SQL verification | Runtime crash | **Compile-time error** |
| Cursor management | Manual | Automatic |
| Boilerplate | High | Minimal |
| LiveData support | No | Yes |
| Migration helpers | None | Built-in |

---

## Room Architecture

```
Activity / ViewModel
        ↓
      @Dao  (interface — defines queries)
        ↓
   @Database  (abstract class — singleton instance)
        ↓
    SQLite file on device storage
```

---

## The Three Room Components

### @Entity
The `@Entity` annotation marks a plain Java class (POJO — Plain Old Java Object) as a database table. Each field in the class becomes a column in the table.

```java
@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "content")
    public String content;
}
```

- `@PrimaryKey(autoGenerate = true)` — marks the primary key and auto-increments its value on every insert
- `@ColumnInfo(name = "...")` — specifies a custom column name (optional; defaults to field name)
- `tableName` in `@Entity` — specifies the SQL table name (optional; defaults to class name)

### @Dao (Data Access Object)
The `@Dao` annotation marks an interface whose methods represent database operations. Room generates the full implementation at **compile time** — you never write the SQL execution code yourself.

```java
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
}
```

- `@Insert` — Room generates the INSERT SQL automatically
- `@Update` — Room generates UPDATE based on the entity's primary key
- `@Delete` — Room generates DELETE based on the entity's primary key
- `@Query("...")` — you write the SQL; Room verifies it at compile time

### @Database
The `@Database` annotation marks an abstract class that extends `RoomDatabase`. It:
- Lists all `@Entity` classes that belong to this database
- Specifies the database version number
- Declares abstract methods that return DAO instances

```java
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();

    // Singleton implementation
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

---

## Threading in Room

By default, Room **does not allow database operations on the main (UI) thread**. Database I/O can take unpredictable time and blocking the UI thread causes ANR (Application Not Responding) errors.

You must execute all DAO calls on a background thread:

```java
ExecutorService executor = Executors.newSingleThreadExecutor();

executor.execute(() -> {
    db.noteDao().insert(new Note("Title", "Content"));
    // After DB operation, update UI on main thread:
    runOnUiThread(() -> refreshList());
});
```

Alternatives:
- **Kotlin Coroutines with `Dispatchers.IO`** — recommended for Kotlin
- **`LiveData` return types in DAO** — Room automatically moves observation to a background thread

---

## Singleton Pattern

The database instance is created as a **singleton** — only one instance exists for the entire app lifecycle. This is achieved using **double-checked locking**:

1. First null check — avoids the overhead of `synchronized` for subsequent calls
2. `synchronized` block — ensures only one thread can create the instance
3. Second null check — prevents a race condition where two threads both passed the first check

Creating multiple database instances wastes memory and can cause inconsistent data reads.

---

## Database Versioning and Migration

When you modify the database schema (add a table, add a column), you must **increment the version number** in `@Database`. Room will then call the migration you provide, allowing existing data to be preserved. Without a migration, Room will throw an error at runtime (or destroy and recreate the database if `.fallbackToDestructiveMigration()` is set).
