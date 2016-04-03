package com.example.android.twitterclient;

import com.f2prateek.rx.preferences.Preference;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import rx.Observable;
import rx.schedulers.Schedulers;

@Singleton
public class TweetGateway {
    private final TweetPersistence tweetPersistence;
    private final TwitterApi twitterApi;
    private final Preference<String> sincePreference;

    @Inject
    public TweetGateway(TweetPersistence tweetPersistence, TwitterApi twitterApi,
            @Named("LatestTweetsTimestamp") Preference<String> sincePreference) {
        this.tweetPersistence = tweetPersistence;
        this.twitterApi = twitterApi;
        this.sincePreference = sincePreference;
    }

    public Observable<List<Tweet>> get() {
        twitterApi.getTweets(sincePreference.get())
                .doOnNext(response -> sincePreference.set(response.timestamp))
                .map(response -> response.tweets)
                .subscribeOn(Schedulers.io())
                .subscribe(tweetPersistence::addAll);
        return tweetPersistence.asObservable();
    }

    public Observable<Void> add(Tweet tweet) {
        return twitterApi.postTweet(tweet)
                .flatMap(postedTweet -> twitterApi.getTweets(sincePreference.get()))
                .map(t -> null);
    }
}
