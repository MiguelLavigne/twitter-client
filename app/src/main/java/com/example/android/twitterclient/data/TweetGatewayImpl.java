package com.example.android.twitterclient.data;

import com.example.android.twitterclient.domain.Tweet;
import com.example.android.twitterclient.domain.TweetGateway;
import com.example.android.twitterclient.domain.TweetPersistence;
import com.example.android.twitterclient.domain.TwitterApi;
import com.example.android.twitterclient.util.Preference;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class TweetGatewayImpl implements TweetGateway {
    private final TweetPersistence tweetPersistence;
    private final TwitterApi twitterApi;
    private final Preference<String> since;

    @Inject
    public TweetGatewayImpl(TweetPersistence tweetPersistence, TwitterApi twitterApi,
            @Named("LatestTweetsTimestamp") Preference<String> since) {
        this.tweetPersistence = tweetPersistence;
        this.twitterApi = twitterApi;
        this.since = since;
    }

    @Override
    public Observable<List<Tweet>> get() {
        final Subscription subscription = twitterApi.getTweets(since.get())
                .doOnNext(response -> since.set(response.timestamp))
                .map(response -> response.tweets)
                .onErrorResumeNext(Observable.empty())
                .subscribeOn(Schedulers.io())
                .subscribe(tweetPersistence::addAll);
        return tweetPersistence.asObservable().doOnUnsubscribe(subscription::unsubscribe);
    }

    @Override
    public Observable<Void> add(Tweet tweet) {
        return twitterApi.postTweet(tweet)
                .doOnNext(tweetPersistence::add)
                .map(t -> null);
    }
}
