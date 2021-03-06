package com.example.android.twitterclient.domain;

import java.util.List;

public class GetTweetResponse {
    public final List<Tweet> tweets;
    public final String timestamp;

    public GetTweetResponse(List<Tweet> tweets, String timestamp) {
        this.tweets = tweets;
        this.timestamp = timestamp;
    }
}
