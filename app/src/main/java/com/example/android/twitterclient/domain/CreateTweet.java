package com.example.android.twitterclient.domain;

import com.example.android.twitterclient.data.TweetGateway;
import com.example.android.twitterclient.util.DateProvider;
import com.f2prateek.rx.preferences.Preference;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CreateTweet {
    private final DateProvider dateProvider;
    private final TweetGateway tweetGateway;
    private final Preference<String> user;

    @Inject
    public CreateTweet(DateProvider dateProvider, TweetGateway tweetGateway, @Named("user") Preference<String> user) {
        this.dateProvider = dateProvider;
        this.tweetGateway = tweetGateway;
        this.user = user;
    }

    public Observable<Void> execute(String tweetMessage) {
        return tweetGateway.add(new Tweet(user.get(), tweetMessage, dateProvider.getTime()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
