# Experiment 5: SQLite Database Implementation

## Overview
This experiment demonstrates working with **SQLite Database** in Android, including database creation, management, and **CRUD operations** (Create, Read, Update, Delete). It shows how to use **SQLiteOpenHelper** for efficient database management.

## Key Concepts

### 1. SQLite Database
- Android's built-in relational database
- Lightweight and efficient for mobile apps
- Stored locally on the device

### 2. SQLiteOpenHelper
- Helper class for database creation and versioning
- onCreate() for initial database setup
- onUpgrade() for handling schema changes
- Manages database connections safely

### 3. CRUD Operations
- **Create**: Insert new records using INSERT statements
- **Read**: Query records using SELECT statements
- **Update**: Modify existing records (to be implemented)
- **Delete**: Remove records (to be implemented)

### 4. Database Operations
- ContentValues for data insertion
- Cursor for result retrieval
- Transaction management
- Database queries with WHERE clauses

## What You'll Learn

1. ✓ How to create and initialize a SQLite database
2. ✓ How to extend SQLiteOpenHelper
3. ✓ How to insert data into database tables
4. ✓ How to retrieve data using Cursor
5. ✓ How to manage database lifecycle
6. ✓ How to build complete CRUD applications

## Main Activity
- **Database.java** - Main activity with database operations UI

## How to Run

1. Open the project in Android Studio
2. Build and run on an emulator or physical device
3. Use the UI to add and view database records
4. Observe data persistence across app sessions
5. Monitor database state through Logcat

## Files Structure
```
Exp5/
├── app/src/main/
│   ├── java/com/storm/exp5/
│   │   ├── Database.java
│   │   └── DatabaseHelper.java
│   ├── res/layout/
│   │   └── activity_database.xml
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Related Android Concepts
- Database: `SQLiteDatabase`, `SQLiteOpenHelper`
- Data Storage: `ContentValues`, `Cursor`
- Database Schema: Table creation, columns, data types
- Query Operations: SELECT, INSERT, UPDATE, DELETE
- UI Components: `EditText`, `Button`, `TextView`
