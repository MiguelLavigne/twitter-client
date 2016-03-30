package com.example.android.twitterclient;

import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(ListTweetView injectee);

    void inject(ComposeTweetView injectee);
}
