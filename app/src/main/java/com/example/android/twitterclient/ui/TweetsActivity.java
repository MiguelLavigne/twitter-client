package com.example.android.twitterclient.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.example.android.twitterclient.App;
import com.example.android.twitterclient.R;
import javax.inject.Inject;

public class TweetsActivity extends AppCompatActivity {
    @Inject SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplicationContext()).component().inject(this);
        setContentView(R.layout.activity_tweets);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tweets_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_tweets:
                sharedPreferences.edit().clear().commit();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
