package com.example.android.twitterclient;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

@Singleton
public class TweetGateway {
    private final TweetPersistence tweetPersistence;

    @Inject
    public TweetGateway(TweetPersistence tweetPersistence) {
        this.tweetPersistence = tweetPersistence;
    }

    public Observable<List<Tweet>> get() {
        return tweetPersistence.getAll();
    }

    public void add(Tweet tweet) {
        tweetPersistence.add(tweet);
    }
}
