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
