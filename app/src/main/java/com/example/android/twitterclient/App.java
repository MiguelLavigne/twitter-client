package com.example.android.twitterclient;

import android.app.Application;
import com.example.android.twitterclient.data.TwitterApiPersistence;
import com.facebook.stetho.Stetho;
import javax.inject.Inject;
import net.danlew.android.joda.JodaTimeAndroid;

public class App extends Application {
    @Inject TwitterApiPersistence twitterApiPersistence;

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        component().inject(this);
        JodaTimeAndroid.init(this);
        Stetho.initializeWithDefaults(this);
    }

    public AppComponent component() {
        return component;
    }
}
