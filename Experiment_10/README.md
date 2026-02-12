# Experiment 10: File Handling using App-Specific Storage / SAF

## Aim

Read and write files using internal storage or Storage Access Framework with proper permissions.

## Objective

To understand file system access and data persistence on device storage.

## Outcome

Students will be able to manage files securely within the Android environment.

## Process Flow

1. For internal storage: Use `openFileOutput` and `openFileInput`.
2. For external/shared storage: Use `Intent.ACTION_CREATE_DOCUMENT` or `Intent.ACTION_OPEN_DOCUMENT`.
3. Handle runtime permissions if accessing shared storage (though SAF avoids this for user-selected files).
4. Implement File Input/Output streams to read/write data.

## Code

### `MainActivity.java` (Internal Storage)

```java
String filename = "myfile";
String fileContents = "Hello world!";
try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
    fos.write(fileContents.getBytes());
} catch (Exception e) {
    e.printStackTrace();
}
```

### `MainActivity.java` (SAF - Create File)

```java
// Logic to create a file using SAF
Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
intent.addCategory(Intent.CATEGORY_OPENABLE);
intent.setType("text/plain");
intent.putExtra(Intent.EXTRA_TITLE, "myfile.txt");
startActivityForResult(intent, WRITE_REQUEST_CODE);
```
