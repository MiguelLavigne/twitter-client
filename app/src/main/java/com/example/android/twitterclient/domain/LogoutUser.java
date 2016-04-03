package com.example.android.twitterclient.domain;

import com.example.android.twitterclient.data.TweetPersistence;
import com.f2prateek.rx.preferences.Preference;
import javax.inject.Inject;
import javax.inject.Named;

public class LogoutUser {
    private final Preference<String> user;
    private final TweetPersistence tweetPersistence;
    private final Preference<String> latestTweetsTimestamp;

    @Inject
    public LogoutUser(@Named("user") Preference<String> user, TweetPersistence tweetPersistence,
            @Named("LatestTweetsTimestamp") Preference<String> latestTweetsTimestamp) {
        this.user = user;
        this.tweetPersistence = tweetPersistence;
        this.latestTweetsTimestamp = latestTweetsTimestamp;
    }

    public void execute() {
        user.delete();
        latestTweetsTimestamp.delete();
        tweetPersistence.clear();
    }
}
