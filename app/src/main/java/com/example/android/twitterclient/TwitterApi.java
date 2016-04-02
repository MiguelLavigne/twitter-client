package com.example.android.twitterclient;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface TwitterApi {
    @POST("/user/tweet")
    Observable<Tweet> postTweet(Tweet tweet);

    @GET("/user/tweet")
    Observable<List<Tweet>> getTweets();
}
