# Experiment 8: Local Database Application using Room

## Aim

Implement CRUD operations using Room persistence library for structured data storage.

## Objective

To learn how to persist data locally using the Room database.

## Outcome

Students will be able to create, read, update, and delete data in a local database.

## Process Flow

1. Add Room dependencies to `build.gradle`.
2. Create an Entity class `@Entity`.
3. Create a DAO interface `@Dao` with methods for CRUD operations.
4. Create an abstract Database class `@Database`.
5. Initialize the database instance in `MainActivity` and perform operations asynchronously.

## Code

### `User.java` (Entity)

```java
package com.csit.experiment_8;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;
}
```

### `UserDao.java` (DAO)

```java
package com.csit.experiment_8;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
```

### `AppDatabase.java`

```java
package com.csit.experiment_8;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app-database").build();
        }
        return instance;
    }
}
```

### `MainActivity.java`

```java
package com.csit.experiment_8;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = AppDatabase.getInstance(this);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            User user = new User();
            user.uid = 1;
            user.firstName = "John";
            user.lastName = "Doe";
            db.userDao().insertAll(user);
        });
    }
}
```
