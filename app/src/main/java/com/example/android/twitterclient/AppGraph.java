package com.example.android.twitterclient;

import com.example.android.twitterclient.ui.ComposeTweetView;
import com.example.android.twitterclient.ui.LoginView;
import com.example.android.twitterclient.ui.TweetsActivity;
import com.example.android.twitterclient.ui.TweetsView;

public interface AppGraph {
    void inject(App injectee);

    void inject(TweetsActivity injectee);

    void inject(TweetsView injectee);

    void inject(ComposeTweetView injectee);

    void inject(LoginView injectee);
}
