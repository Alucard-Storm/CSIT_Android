# Experiment 7: Dynamic List Application using RecyclerView

## Aim

Develop an application displaying a list of items using RecyclerView and custom adapters.

## Objective

To understand how to display large sets of data efficiently using RecyclerView.

## Outcome

Students will be able to implement dynamic lists and handle item clicks.

## Process Flow

1. Add `androidx.recyclerview:recyclerview` dependency to `build.gradle`.
2. Add `RecyclerView` to `activity_main.xml`.
3. Create a layout for individual items (e.g., `item_view.xml`).
4. Create a Data Model class.
5. Implement a `RecyclerView.Adapter` and `ViewHolder`.
6. Bind the adapter to the RecyclerView in `MainActivity.java`.

## Code

### `MainActivity.java`

```java
package com.csit.experiment_7;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> data = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            data.add("Item " + i);
        }

        MyAdapter adapter = new MyAdapter(data);
        recyclerView.setAdapter(adapter);
    }
}
```
