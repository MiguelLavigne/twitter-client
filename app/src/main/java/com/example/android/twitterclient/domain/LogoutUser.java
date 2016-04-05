package com.example.android.twitterclient.domain;

import com.example.android.twitterclient.data.TweetPersistence;
import com.example.android.twitterclient.data.UserPersistence;
import com.f2prateek.rx.preferences.Preference;
import javax.inject.Inject;
import javax.inject.Named;

public class LogoutUser {
    private final UserPersistence user;
    private final TweetPersistence tweetPersistence;
    private final Preference<String> latestTweetsTimestamp;

    @Inject
    public LogoutUser(UserPersistence userPersistence, TweetPersistence tweetPersistence,
            @Named("LatestTweetsTimestamp") Preference<String> latestTweetsTimestamp) {
        this.user = userPersistence;
        this.tweetPersistence = tweetPersistence;
        this.latestTweetsTimestamp = latestTweetsTimestamp;
    }

    public void execute() {
        user.clear();
        latestTweetsTimestamp.delete();
        tweetPersistence.clear();
    }
}
