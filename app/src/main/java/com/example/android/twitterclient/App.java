package com.example.android.twitterclient;

import android.app.Application;

public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.create();
    }

    public AppComponent component() {
        return component;
    }
}
