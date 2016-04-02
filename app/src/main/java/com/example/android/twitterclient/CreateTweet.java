package com.example.android.twitterclient;

import javax.inject.Inject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CreateTweet {
    private final DateProvider dateProvider;
    private final TweetGateway tweetGateway;

    @Inject
    public CreateTweet(DateProvider dateProvider, TweetGateway tweetGateway) {
        this.dateProvider = dateProvider;
        this.tweetGateway = tweetGateway;
    }

    public Observable<Void> execute(CreateTweetRequest request) {
        return tweetGateway.add(new Tweet(request.user, request.message, dateProvider.getTime()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
