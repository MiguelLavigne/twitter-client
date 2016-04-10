package com.example.android.twitterclient.domain;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface TwitterApi {
    @POST("user/login")
    Observable<LoginResponse> login(@Query("username") String username, @Query("password") String password);

    @POST("user/tweets/new")
    Observable<Tweet> postTweet(@Body Tweet tweet);

    @GET("user/tweets")
    Observable<GetTweetResponse> getTweets(@Query("since") String since);
}
