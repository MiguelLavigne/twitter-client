package com.example.android.twitterclient;

import android.app.Application;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import rx.Observable;

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

        //intervalFakeTweets();
    }

    public AppComponent component() {
        return component;
    }

    private void intervalFakeTweets() {
        Observable.interval(0, 30, TimeUnit.SECONDS).subscribe(value -> {
            String userStr = String.format("user%d", (value % 5) + 1);
            twitterApiPersistence.add(new Tweet(userStr, "Generated tweet messages, don't expect much here " + value));
        });
    }
}
