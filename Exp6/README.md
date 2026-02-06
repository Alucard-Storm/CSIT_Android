# Experiment 6: RSS Feed Reader

## Overview
This experiment demonstrates **RSS feed parsing** and **network operations** in Android. It combines **asynchronous tasks**, **XML parsing**, and **list views** to create a functional RSS feed reader application.

## Key Concepts

### 1. Asynchronous Operations
- AsyncTask for background network operations
- Prevents UI blocking during network requests
- doInBackground() for heavy operations
- onPostExecute() for UI updates with results

### 2. XML Parsing
- Using XmlPullParser for efficient XML parsing
- Lightweight and event-driven parsing
- Parsing RSS feed structure (channel, item, title, description, link)

### 3. Network Requests
- Opening URL connections to RSS feed sources
- Handling InputStream from network
- Managing network exceptions and errors

### 4. ListView Display
- SimpleAdapter for displaying parsed data
- HashMap for storing feed items
- Dynamic list updates with parsed content

### 5. RSS Feed Format
- Channel: Contains feed metadata
- Items: Individual articles/posts
- Elements: title, description, link, pubDate

## What You'll Learn

1. ✓ How to fetch data from network asynchronously
2. ✓ How to parse XML using XmlPullParser
3. ✓ How to work with AsyncTask
4. ✓ How to display dynamic content in ListView
5. ✓ How to handle network errors gracefully

## Main Activity
- **RSSFeed.java** - Main activity with feed fetching and parsing

## How to Run

1. Open the project in Android Studio
2. Ensure internet permission is granted
3. Build and run on an emulator or physical device
4. Feed data will be fetched and displayed in the list
5. Tap items to view full content

## Files Structure
```
Exp6/
├── app/src/main/
│   ├── java/com/storm/exp6/
│   │   ├── RSSFeed.java
│   │   └── FeedParser.java
│   ├── res/layout/
│   │   ├── activity_rssfeed.xml
│   │   └── list_item.xml
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Related Android Concepts
- Asynchronous Tasks: `AsyncTask` class
- XML Parsing: `XmlPullParser`, `XmlPullParserFactory`
- Network: `URL`, `URLConnection`, `InputStream`
- List Display: `ListView`, `SimpleAdapter`
- Permissions: `INTERNET` permission
