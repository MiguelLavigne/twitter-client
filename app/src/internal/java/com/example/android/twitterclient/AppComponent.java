package com.example.android.twitterclient;

import com.example.android.twitterclient.data.DataModule;
import com.facebook.stetho.Stetho;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { AppModule.class, InternalNetModule.class, DataModule.class })
public interface AppComponent extends AppGraph {
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
