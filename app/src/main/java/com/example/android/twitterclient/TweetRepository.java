package com.example.android.twitterclient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.subjects.BehaviorSubject;

@Singleton
public class TweetRepository {
    private List<Tweet> tweets = new ArrayList<>();
    private BehaviorSubject<List<Tweet>> subject = BehaviorSubject.create();
    private int counter = 1;

    @Inject
    public TweetRepository() {
        tweets.add(new Tweet("user1", "My tweetButton message " + counter++));
        subject.onNext(tweets);
        Observable.interval(15, TimeUnit.SECONDS).subscribe(aLong -> {
            tweets.add(new Tweet("user1", "My tweetButton message " + counter++));
            subject.onNext(tweets);
        });
    }

    public Observable<List<Tweet>> get() {
        return subject.asObservable();
    }

    public void save(Tweet tweet) {
        tweets.add(tweet);
        subject.onNext(tweets);
    }
}
