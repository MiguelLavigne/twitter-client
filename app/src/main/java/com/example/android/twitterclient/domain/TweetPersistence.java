package com.example.android.twitterclient.domain;

import java.util.List;
import rx.Observable;

public interface TweetPersistence {
    void addAll(List<Tweet> tweets);

    Observable<List<Tweet>> asObservable();

    void clear();

    void add(Tweet tweet);
}
