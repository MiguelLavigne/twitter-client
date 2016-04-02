package com.example.android.twitterclient;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.schedulers.Schedulers;

@Singleton
public class TweetGateway {
    private final TweetPersistence tweetPersistence;
    private final TwitterApi twitterApi;

    @Inject
    public TweetGateway(TweetPersistence tweetPersistence, TwitterApi twitterApi) {
        this.tweetPersistence = tweetPersistence;
        this.twitterApi = twitterApi;
    }

    public Observable<List<Tweet>> get() {
        twitterApi.getTweets()
                .subscribeOn(Schedulers.io())
                .subscribe(tweetPersistence::addAll);
        return tweetPersistence.asObservable();
    }

    public Observable<Void> add(Tweet tweet) {
        return twitterApi.postTweet(tweet)
                .doOnNext(tweetPersistence::add)
                .map(t -> null);
    }
}
