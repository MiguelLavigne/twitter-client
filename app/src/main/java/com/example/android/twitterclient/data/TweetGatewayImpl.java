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
import rx.subjects.BehaviorSubject;

public class TweetGatewayImpl implements TweetGateway {
    private final TweetPersistence tweetPersistence;
    private final TwitterApi twitterApi;
    private final Preference<String> since;

    private BehaviorSubject<Boolean> refreshingState = BehaviorSubject.create();

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
                .doOnSubscribe(() -> refreshingState.onNext(Boolean.TRUE))
                .doOnTerminate(() -> refreshingState.onNext(Boolean.FALSE))
                .subscribe(tweetPersistence::addAll);
        return tweetPersistence.asObservable().doOnUnsubscribe(subscription::unsubscribe);
    }

    @Override
    public Observable<Void> add(Tweet tweet) {
        return twitterApi.postTweet(tweet)
                .doOnNext(tweetPersistence::add)
                .map(t -> null);
    }

    @Override
    public void forceRefresh() {
        twitterApi.getTweets(since.get())
                .doOnNext(response -> since.set(response.timestamp))
                .map(response -> response.tweets)
                .onErrorResumeNext(Observable.empty())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> refreshingState.onNext(Boolean.TRUE))
                .doOnTerminate(() -> refreshingState.onNext(Boolean.FALSE))
                .subscribe(tweetPersistence::addAll);
    }

    @Override
    public Observable<Boolean> refreshingStateObservable() {
        return refreshingState.asObservable();
    }
}
