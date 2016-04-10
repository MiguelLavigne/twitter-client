package com.example.android.twitterclient.data;

import com.example.android.twitterclient.App;
import com.example.android.twitterclient.AppModule;
import com.example.android.twitterclient.ui.ComposeTweetView;
import com.example.android.twitterclient.ui.LoginView;
import com.example.android.twitterclient.ui.TweetsActivity;
import com.example.android.twitterclient.ui.TweetsView;
import com.facebook.stetho.Stetho;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { AppModule.class, MockNetModule.class, DataModule.class })
public interface AppComponent {
    void inject(App injectee);

    void inject(TweetsActivity injectee);

    void inject(TweetsView injectee);

    void inject(ComposeTweetView injectee);

    void inject(LoginView injectee);

    final class Initializer {
        Initializer() {
            throw new AssertionError("use statically");
        }

        public static AppComponent init(App app) {
            Stetho.initializeWithDefaults(app);

            return DaggerAppComponent.builder()
                    .appModule(new AppModule(app))
                    .build();
        }
    }
}
