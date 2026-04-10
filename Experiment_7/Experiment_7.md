# Experiment 7 – Dynamic List Application using RecyclerView

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [← Experiment 6](../Experiment_6/Experiment_6.md) | [Next → Experiment 8](../Experiment_8/Experiment_8.md)

> [**Notes**](./Notes_7.md)

---

## Aim
To develop an Android application displaying a dynamic list of items using `RecyclerView` and a custom adapter.

## Objective
- Implement `RecyclerView` with `LinearLayoutManager`
- Create a custom `RecyclerView.Adapter` with `ViewHolder` pattern
- Add and delete items dynamically
- Handle item click events

## Prerequisites / Theory

### RecyclerView vs ListView
| Feature | ListView | RecyclerView |
|---|---|---|
| ViewHolder | Optional (manual) | Mandatory (enforced) |
| Animations | None built-in | Built-in item animators |
| Layout types | Vertical only | Linear, Grid, Staggered |
| Performance | Slower (redundant `findViewbyId`) | Better (view recycling) |

### Key Components
| Component | Role |
|---|---|
| `RecyclerView` | Scrollable container |
| `RecyclerView.Adapter<VH>` | Binds data to views |
| `RecyclerView.ViewHolder` | Caches view references |
| `LayoutManager` | Determines arrangement (Linear / Grid) |

### ViewHolder Pattern
```java
class MyViewHolder extends RecyclerView.ViewHolder {
    TextView tvName;
    MyViewHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName); // cached once
    }
}
```

---

## Procedure
1. Add RecyclerView dependency in `build.gradle`
2. Create a `Student` data model class
3. Create `item_student.xml` for individual list items
4. Implement `StudentAdapter` extending `RecyclerView.Adapter`
5. Wire everything in `MainActivity` with add/delete functionality

---

## Code

**build.gradle (module) — dependencies:**
```groovy
implementation 'androidx.recyclerview:recyclerview:1.3.2'
```

**Student.java (Data Model):**
```java
package com.example.recyclerviewdemo;

public class Student {
    private int id;
    private String name;
    private String branch;

    public Student(int id, String name, String branch) {
        this.id     = id;
        this.name   = name;
        this.branch = branch;
    }

    public int    getId()     { return id; }
    public String getName()   { return name; }
    public String getBranch() { return branch; }
}
```

**res/layout/item_student.xml:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<com.google.android.material.card.MaterialCardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp"
    app:cardCornerRadius="8dp"
    app:cardElevation="3dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="12dp"
        android:gravity="center_vertical">

        <TextView
            android:id="@+id/tvId"
            android:layout_width="40dp"
            android:layout_height="40dp"
            android:gravity="center"
            android:textStyle="bold"
            android:textColor="#FFFFFF"
            android:background="@drawable/circle_bg"
            android:textSize="14sp"/>

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical"
            android:layout_marginStart="12dp">

            <TextView android:id="@+id/tvName"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="16sp" android:textStyle="bold"
                android:textColor="#212121"/>

            <TextView android:id="@+id/tvBranch"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="13sp" android:textColor="#757575"/>
        </LinearLayout>

        <com.google.android.material.button.MaterialButton
            android:id="@+id/btnDelete"
            style="@style/Widget.MaterialComponents.Button.TextButton"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Remove"
            android:textColor="#B00020"/>
    </LinearLayout>
</com.google.android.material.card.MaterialCardView>
```

**res/drawable/circle_bg.xml:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="oval">
    <solid android:color="#6200EE"/>
</shape>
```

**StudentAdapter.java:**
```java
package com.example.recyclerviewdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private final List<Student> students;

    public StudentAdapter(List<Student> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.tvId.setText(String.valueOf(student.getId()));
        holder.tvName.setText(student.getName());
        holder.tvBranch.setText(student.getBranch());

        holder.btnDelete.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;
            students.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, students.size());
        });

        holder.itemView.setOnClickListener(v -> {
            // Item click handled by caller if needed
        });
    }

    @Override
    public int getItemCount() { return students.size(); }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvBranch;
        MaterialButton btnDelete;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId      = itemView.findViewById(R.id.tvId);
            tvName    = itemView.findViewById(R.id.tvName);
            tvBranch  = itemView.findViewById(R.id.tvBranch);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
```

**activity_main.xml:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent" android:layout_height="match_parent"
    android:orientation="vertical">

    <LinearLayout android:layout_width="match_parent"
        android:layout_height="wrap_content" android:orientation="horizontal"
        android:padding="12dp" android:background="#6200EE">

        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/etStudentName"
            android:layout_width="0dp" android:layout_height="wrap_content"
            android:layout_weight="1" android:hint="Student Name"
            android:background="#FFFFFF" android:padding="8dp"
            android:layout_marginEnd="8dp"/>

        <com.google.android.material.button.MaterialButton
            android:id="@+id/btnAdd"
            android:layout_width="wrap_content" android:layout_height="wrap_content"
            android:text="Add" android:backgroundTint="#FFFFFF"
            android:textColor="#6200EE"/>
    </LinearLayout>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="4dp"/>
</LinearLayout>
```

**MainActivity.java:**
```java
package com.example.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Student> studentList = new ArrayList<>();
    StudentAdapter adapter;
    int nextId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Seed data
        studentList.addAll(Arrays.asList(
            new Student(nextId++, "Akshay Sagar",  "CS & IT"),
            new Student(nextId++, "Pawan Tiwari",    "CS & IT"),
            new Student(nextId++, "Diksha Pawar",    "CS & IT"),
            new Student(nextId++, "Divya Khade",    "CS & IT")
        ));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new StudentAdapter(studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        TextInputEditText etName = findViewById(R.id.etStudentName);
        MaterialButton btnAdd   = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText() != null ? etName.getText().toString().trim() : "";
            if (name.isEmpty()) {
                Toast.makeText(this, "Enter a name!", Toast.LENGTH_SHORT).show();
                return;
            }
            studentList.add(new Student(nextId++, name, "CS & IT"));
            adapter.notifyItemInserted(studentList.size() - 1);
            recyclerView.scrollToPosition(studentList.size() - 1);
            etName.setText("");
        });
    }
}
```

---

## Output
A list of student cards with ID circle, name, branch, and a Remove button. New students can be added via the top input field. Tapping Remove deletes that card with a smooth animation.

## Viva Questions
1. What is `RecyclerView`? How is it better than `ListView`?
2. What is the `ViewHolder` pattern? Why is it used?
3. What is `onCreateViewHolder()` vs `onBindViewHolder()`?
4. What is `notifyItemInserted()` vs `notifyDataSetChanged()`? Which is better?
5. What is a `LayoutManager`? Name three types.
6. What is `getAdapterPosition()` in a ViewHolder?

## Result
An Android application with a RecyclerView displaying a dynamic student list with add and remove functionality using a custom adapter and ViewHolder pattern was successfully implemented.
