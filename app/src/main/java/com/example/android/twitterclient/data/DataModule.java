package com.example.android.twitterclient.data;

import com.example.android.twitterclient.domain.TweetGateway;
import com.example.android.twitterclient.domain.TweetPersistence;
import com.example.android.twitterclient.domain.UserPersistence;
import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {
    @Provides
    public TweetGateway provideTweetGateway(TweetGatewayImpl impl) {
        return impl;
    }

    @Provides
    public TweetPersistence provideTweetPersistence(SharedPreferenceTweetPersistence impl) {
        return impl;
    }

    @Provides
    public UserPersistence provideUserPersistence(SharedPreferenceUserPersistence impl) {
        return impl;
    }
}
