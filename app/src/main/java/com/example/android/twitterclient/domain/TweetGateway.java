package com.example.android.twitterclient.domain;

import java.util.List;
import rx.Observable;

public interface TweetGateway {
    Observable<List<Tweet>> get();

    Observable<Void> add(Tweet tweet);
}
