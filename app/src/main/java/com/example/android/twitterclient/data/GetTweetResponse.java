package com.example.android.twitterclient.data;

import com.example.android.twitterclient.domain.Tweet;
import java.util.List;

public class GetTweetResponse {
    public final List<Tweet> tweets;
    public final String timestamp;

    public GetTweetResponse(List<Tweet> tweets, String timestamp) {
        this.tweets = tweets;
        this.timestamp = timestamp;
    }
}
