package com.example.android.twitterclient.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.example.android.twitterclient.App;
import com.example.android.twitterclient.R;
import com.example.android.twitterclient.data.TweetPersistence;
import com.example.android.twitterclient.data.TwitterApiPersistence;
import com.example.android.twitterclient.domain.LogoutUser;
import com.f2prateek.rx.preferences.Preference;
import javax.inject.Inject;
import javax.inject.Named;

public class TweetsActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_LOGIN = 1;
    @Inject TwitterApiPersistence twitterApiPersistence;
    @Inject @Named("user") Preference<String> user;
    @Inject LogoutUser logoutUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplicationContext()).component().inject(this);
        setContentView(R.layout.activity_tweets);

        if (savedInstanceState == null && !isLoggedIn()) {
            startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_CODE_LOGIN);
        }
    }

    private boolean isLoggedIn() {
        //noinspection ConstantConditions
        return user.get() != null && !user.get().isEmpty();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE_LOGIN || resultCode != Activity.RESULT_OK) {
            finish();
        }
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
                twitterApiPersistence.clear();
                return true;
            case R.id.logout:
                logoutUser.execute();
                finish();
                startActivity(new Intent(this, TweetsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
