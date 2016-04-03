package com.example.android.twitterclient;

import com.example.android.twitterclient.data.NetModule;
import com.example.android.twitterclient.ui.ComposeTweetView;
import com.example.android.twitterclient.ui.LoginView;
import com.example.android.twitterclient.ui.TweetsActivity;
import com.example.android.twitterclient.ui.TweetsView;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { AppModule.class, NetModule.class })
public interface AppComponent {
    void inject(App injectee);

    void inject(TweetsActivity injectee);

    void inject(TweetsView injectee);

    void inject(ComposeTweetView injectee);

    void inject(LoginView injectee);
}
