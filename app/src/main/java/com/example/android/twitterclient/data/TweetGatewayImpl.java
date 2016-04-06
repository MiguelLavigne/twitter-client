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
import rx.schedulers.Schedulers;

public class TweetGatewayImpl implements TweetGateway {
    private final TweetPersistence tweetPersistence;
    private final TwitterApi twitterApi;
    private final Preference<String> sincePreference;

    @Inject
    public TweetGatewayImpl(TweetPersistence tweetPersistence, TwitterApi twitterApi,
            @Named("LatestTweetsTimestamp") Preference<String> sincePreference) {
        this.tweetPersistence = tweetPersistence;
        this.twitterApi = twitterApi;
        this.sincePreference = sincePreference;
    }

    @Override
    public Observable<List<Tweet>> get() {
        twitterApi.getTweets(sincePreference.get())
                .doOnNext(response -> sincePreference.set(response.timestamp))
                .map(response -> response.tweets)
                .subscribeOn(Schedulers.io())
                .subscribe(tweetPersistence::addAll);
        return tweetPersistence.asObservable();
    }

    @Override
    public Observable<Void> add(Tweet tweet) {
        return twitterApi.postTweet(tweet)
                .doOnNext(t -> tweetPersistence.add(t))
                .map(t -> null);
    }
}
