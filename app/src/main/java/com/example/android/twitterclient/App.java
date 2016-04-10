package com.example.android.twitterclient;

import android.app.Application;
import net.danlew.android.joda.JodaTimeAndroid;

public class App extends Application {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = AppComponent.Initializer.init(this);
        JodaTimeAndroid.init(this);
    }

    public AppComponent component() {
        return component;
    }
}
