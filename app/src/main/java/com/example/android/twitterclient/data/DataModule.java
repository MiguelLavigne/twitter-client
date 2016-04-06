package com.example.android.twitterclient.data;

import com.example.android.twitterclient.domain.TweetGateway;
import com.example.android.twitterclient.domain.TweetPersistence;
import com.example.android.twitterclient.domain.User;
import com.example.android.twitterclient.domain.UserPersistence;
import com.example.android.twitterclient.util.Preference;
import com.example.android.twitterclient.util.PreferenceDelegate;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;

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

    @Provides
    @Singleton
    @Named("LatestTweetsTimestamp")
    public Preference<String> provideLatestTweetTimestamp(RxSharedPreferences sp) {
        return new PreferenceDelegate<>(sp.getString("latest_tweet_timestamp", null));
    }

    @Provides
    public User provideUser(UserPersistence userPersistence) {
        return userPersistence.get();
    }
}
