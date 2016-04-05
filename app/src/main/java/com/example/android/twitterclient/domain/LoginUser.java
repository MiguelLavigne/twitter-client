package com.example.android.twitterclient.domain;

import com.example.android.twitterclient.data.LoginResponse;
import com.example.android.twitterclient.data.TwitterApi;
import com.example.android.twitterclient.data.UserPersistence;
import javax.inject.Inject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginUser {
    private final TwitterApi twitterApi;
    private final UserPersistence userPersistence;

    @Inject
    public LoginUser(TwitterApi twitterApi, UserPersistence userPersistence) {
        this.twitterApi = twitterApi;
        this.userPersistence = userPersistence;
    }

    public Observable<LoginResponse> execute(String username, String password) {
        return twitterApi.login(username, password)
                .doOnNext(response -> {
                    if (response.successful) {
                        userPersistence.save(new User(response.username));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
