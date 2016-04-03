package com.example.android.twitterclient;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class AppModule {
    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(app);
    }

    @Provides
    @Singleton
    @Named("LatestTweetsTimestamp")
    public Preference<String> provideLastestTweetTimestamp(SharedPreferences sp) {
        return RxSharedPreferences.create(sp).getString("latest_tweet_timestamp", null);
    }
}
