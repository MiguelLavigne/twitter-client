package com.example.android.twitterclient.domain;

import java.util.List;
import rx.Observable;

public interface TweetGateway {
    interface RefreshState {
        boolean isDone();

        boolean isSuccessful();
    }

    Observable<List<Tweet>> get();

    Observable<Void> add(Tweet tweet);

    void forceRefresh();

    Observable<RefreshState> refreshingStateObservable();
}
