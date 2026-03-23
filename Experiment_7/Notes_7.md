# Practical 7 – Dynamic List Application using RecyclerView

**Course:** CSIT-606 Android Programming | **RGPV VI Semester**

> [Back to Index](../README.md) | [Back to Experiment 7](./Experiment_7.md)

---

## What is RecyclerView?

`RecyclerView` is a flexible and efficient widget for displaying large datasets in a scrollable list. It is the modern replacement for the older `ListView` and `GridView` widgets. The name comes from its core mechanism: it **recycles** off-screen item views instead of creating new ones for each item, making it much more memory-efficient.

---

## Why RecyclerView over ListView?

`ListView` had several limitations:
- The ViewHolder pattern was optional and had to be manually implemented — many developers skipped it, causing performance issues
- It only supported vertical scrolling in a single-column list
- It provided no built-in animations for item additions or removals
- It was less efficient when handling large or dynamic datasets

`RecyclerView` enforces the **ViewHolder pattern** through its architecture, supports multiple layout managers (list, grid, staggered), has built-in item change animations, and integrates cleanly with `DiffUtil` for efficient updates.

| Feature | ListView | RecyclerView |
|---|---|---|
| ViewHolder | Optional | Mandatory |
| Animations | None | Built-in |
| Layout options | Vertical only | Linear, Grid, Staggered |
| Performance | Slower | Significantly better |
| `DiffUtil` support | No | Yes |

---

## ViewHolder Pattern

The **ViewHolder pattern** is a performance optimization that avoids calling `findViewById()` on every scroll event.

In `ListView`, `findViewById()` traversed the entire view hierarchy to locate a widget — an expensive operation that was called every time an item scrolled into view. This caused noticeable lag on large lists.

The ViewHolder is an inner static class that stores references to all the views in a single list item. These references are found once in `onCreateViewHolder()` and cached in the ViewHolder. Then, in `onBindViewHolder()`, the data is simply assigned to these already-found references without any further traversal.

```java
static class MyViewHolder extends RecyclerView.ViewHolder {
    TextView tvName;
    Button btnDelete;

    MyViewHolder(View itemView) {
        super(itemView);
        tvName    = itemView.findViewById(R.id.tvName);    // cached once
        btnDelete = itemView.findViewById(R.id.btnDelete); // cached once
    }
}
```

---

## Adapter

The `RecyclerView.Adapter` is the bridge between the data source and the RecyclerView. It has three mandatory methods:

### `onCreateViewHolder(ViewGroup parent, int viewType)`
Called when the RecyclerView needs a new ViewHolder. This is where you inflate the item layout XML and wrap it in a ViewHolder. This is called infrequently — only when new views are needed.

### `onBindViewHolder(VH holder, int position)`
Called to display data for a specific item at the given position. This is called frequently — every time a view is recycled and reused for a new data item. You must update all views in the holder here.

### `getItemCount()`
Returns the total number of items in the data set. RecyclerView calls this to know how many items to display.

---

## LayoutManager

The `LayoutManager` determines how items are arranged inside the RecyclerView. You must set one before the RecyclerView can display anything.

| LayoutManager | Arrangement |
|---|---|
| `LinearLayoutManager` | Vertical or horizontal scrolling list (default: vertical) |
| `GridLayoutManager(context, 2)` | Fixed number of columns in a grid |
| `StaggeredGridLayoutManager` | Pinterest-style grid with items of varying heights |

```java
recyclerView.setLayoutManager(new LinearLayoutManager(this));
recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns
```

---

## Efficient Data Updates

When the underlying data changes, you must notify the adapter. Use **specific** notify methods rather than the blanket `notifyDataSetChanged()`, which redraws the entire list:

| Method | Use When |
|---|---|
| `notifyItemInserted(pos)` | A single item was added at position `pos` |
| `notifyItemRemoved(pos)` | A single item was removed from position `pos` |
| `notifyItemChanged(pos)` | A single item was updated at position `pos` |
| `notifyItemRangeChanged(start, count)` | Multiple consecutive items were updated |
| `notifyDataSetChanged()` | Entire dataset replaced (no animation, avoid if possible) |

---

## DiffUtil

`DiffUtil` is a utility class that computes the **minimum number of update operations** needed to go from one list to another. It uses the Eugene W. Myers difference algorithm. You provide it with two lists and it tells the adapter exactly which items were added, removed, moved, or changed — resulting in smooth, efficient animations with no full redraws.

`DiffUtil` is the most performant approach for updating large dynamic lists and is commonly used with `ListAdapter`, a subclass of `RecyclerView.Adapter` that handles diffing automatically.

---

## getAdapterPosition() vs getLayoutPosition()

Inside a ViewHolder's click listener, use `holder.getAdapterPosition()` to get the current position of the item in the adapter's data set. Avoid using the `position` parameter from `onBindViewHolder()` directly inside async callbacks because it may be stale after items are inserted or removed.
