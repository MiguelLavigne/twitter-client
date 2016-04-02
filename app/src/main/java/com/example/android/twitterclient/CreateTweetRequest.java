package com.example.android.twitterclient;

import java.util.Date;

public class CreateTweetRequest {
    public final String user;
    public final String message;

    public CreateTweetRequest(String user, String message) {
        this.user = user;
        this.message = message;
    }
}
