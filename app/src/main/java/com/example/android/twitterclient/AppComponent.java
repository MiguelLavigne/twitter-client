package com.example.android.twitterclient;

import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { AppModule.class, NetModule.class })
public interface AppComponent {
    void inject(App injectee);

    void inject(TweetsActivity injectee);

    void inject(TweetsView injectee);

    void inject(ComposeTweetView injectee);
}
