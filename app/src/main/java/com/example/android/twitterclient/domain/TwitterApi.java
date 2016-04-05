package com.example.android.twitterclient.domain;

import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface TwitterApi {
    @POST("/user/login")
    Observable<LoginResponse> login(String username, String password);

    @POST("/user/tweet")
    Observable<Tweet> postTweet(Tweet tweet);

    @GET("/user/tweet")
    Observable<GetTweetResponse> getTweets(String since);
}
