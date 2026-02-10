package com.storm.exp6;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.app.AppCompatActivity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Experiment 7: RSS Feed Reader
 * This activity demonstrates:
 * 1. Asynchronous network operations
 * 2. XML parsing using XmlPullParser
 * 3. ListView with SimpleAdapter
 */
public class RSSFeed extends AppCompatActivity {
    private ListView listView;
    private ArrayList<HashMap<String, String>> feedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_feed);

        // Initialize ListView
        listView = findViewById(R.id.rssListView);

        // Start AsyncTask to fetch RSS feed
        new FetchRSSFeed().execute("https://www.gsmarena.com/rss-news-reviews.php3");
    }

    /**
     * AsyncTask to fetch and parse RSS feed in background
     */
    private class FetchRSSFeed extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                // Open connection to RSS feed
                URL url = new URL(params[0]);
                InputStream inputStream = url.openConnection().getInputStream();

                // Setup XML parser
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(inputStream, null);

                // Parse XML
                int eventType = parser.getEventType();
                String title = "", link = "";

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (parser.getName().equalsIgnoreCase("title")) {
                            title = parser.nextText();
                        } else if (parser.getName().equalsIgnoreCase("link")) {
                            link = parser.nextText();
                        }
                    } else if (eventType == XmlPullParser.END_TAG &&
                            parser.getName().equalsIgnoreCase("item")) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("title", title);
                        map.put("link", link);
                        feedList.add(map);
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Create and set adapter for ListView
            SimpleAdapter adapter = new SimpleAdapter(
                    RSSFeed.this,
                    feedList,
                    android.R.layout.simple_list_item_2,
                    new String[]{"title", "link"},
                    new int[]{android.R.id.text1, android.R.id.text2}
            );
            listView.setAdapter(adapter);
        }
    }
}