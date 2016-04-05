package com.example.android.twitterclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import com.example.android.twitterclient.domain.User;
import com.example.android.twitterclient.domain.UserPersistence;
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
    public App provideApp() {
        return app;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(app);
    }

    @Provides
    @Singleton
    public RxSharedPreferences provideRxSharedPreferences(SharedPreferences sp) {
        return RxSharedPreferences.create(sp);
    }

    @Provides
    @Singleton
    @Named("LatestTweetsTimestamp")
    public Preference<String> provideLatestTweetTimestamp(SharedPreferences sp) {
        return RxSharedPreferences.create(sp).getString("latest_tweet_timestamp", null);
    }

    @Provides
    @Singleton
    public ConnectivityManager provideConnectivityManager() {
        return (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    public User provideUser(UserPersistence userPersistence) {
        return userPersistence.get();
    }
}
