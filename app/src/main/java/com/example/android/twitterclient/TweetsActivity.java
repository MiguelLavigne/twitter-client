package com.example.android.twitterclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import javax.inject.Inject;

public class TweetsActivity extends AppCompatActivity {
    @Inject TweetPersistence tweetPersistence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplicationContext()).component().inject(this);
        setContentView(R.layout.activity_tweets);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add("Clear tweets");
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                tweetPersistence.clear();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
