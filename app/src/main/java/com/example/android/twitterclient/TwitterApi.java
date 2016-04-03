package com.example.android.twitterclient;

import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface TwitterApi {
    @POST("/user/tweet")
    Observable<Tweet> postTweet(Tweet tweet);

    @GET("/user/tweet")
    Observable<GetTweetResponse> getTweets(String since);
}
