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

    private BehaviorSubject<RefreshState> refreshingState = BehaviorSubject.create();

    @Inject
    public TweetGatewayImpl(TweetPersistence tweetPersistence, TwitterApi twitterApi,
            @Named("LatestTweetsTimestamp") Preference<String> since) {
        this.tweetPersistence = tweetPersistence;
        this.twitterApi = twitterApi;
        this.since = since;
    }

    @Override
    public Observable<List<Tweet>> get() {
        final Subscription subscription = refreshRemote();
        return tweetPersistence.asObservable().doOnUnsubscribe(subscription::unsubscribe);
    }

    private Subscription refreshRemote() {
        return twitterApi.getTweets(since.get())
                .doOnNext(response -> since.set(response.timestamp))
                .map(response -> response.tweets)
                .doOnError(t -> refreshingState.onNext(RefreshStateImpl.error()))
                .doOnCompleted(() -> refreshingState.onNext(RefreshStateImpl.done()))
                .doOnSubscribe(() -> refreshingState.onNext(RefreshStateImpl.refreshing()))
                .onErrorResumeNext(Observable.empty())
                .subscribeOn(Schedulers.io())
                .subscribe(tweetPersistence::addAll);
    }

    @Override
    public Observable<Void> add(Tweet tweet) {
        return twitterApi.postTweet(tweet)
                .doOnNext(tweetPersistence::add)
                .map(t -> null);
    }

    @Override
    public void forceRefresh() {
        refreshRemote();
    }

    @Override
    public Observable<RefreshState> refreshingStateObservable() {
        return refreshingState.asObservable();
    }

    private static class RefreshStateImpl implements RefreshState {
        public static RefreshStateImpl refreshing() {
            return new RefreshStateImpl(true, false);
        }

        public static RefreshStateImpl done() {
            return new RefreshStateImpl(false, false);
        }

        public static RefreshStateImpl error() {
            return new RefreshStateImpl(false, true);
        }

        private RefreshStateImpl(boolean isRefreshing, boolean isError) {
            this.isDone = !isRefreshing;
            this.isSuccessful = !isError;
        }

        private boolean isDone;
        private boolean isSuccessful;

        @Override
        public boolean isDone() {
            return isDone;
        }

        @Override
        public boolean isSuccessful() {
            return isSuccessful;
        }
    }
}
