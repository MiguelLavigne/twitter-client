package com.example.android.twitterclient.domain;

import com.example.android.twitterclient.data.LoginResponse;
import com.example.android.twitterclient.data.TwitterApi;
import com.f2prateek.rx.preferences.Preference;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginUser {
    private final TwitterApi twitterApi;
    private final Preference<String> user;

    @Inject
    public LoginUser(TwitterApi twitterApi, @Named("user") Preference<String> user) {
        this.twitterApi = twitterApi;
        this.user = user;
    }

    public Observable<LoginResponse> execute(String username, String password) {
        return twitterApi.login(username, password)
                .doOnNext(response -> {
                    if (response.successful) {
                        user.set(response.username);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
