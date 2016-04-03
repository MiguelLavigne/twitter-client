package com.example.android.twitterclient;

import org.joda.time.DateTime;

public class Tweet {
    public final String user;
    public final String message;
    public final DateTime date;

    public Tweet(String user, String message, DateTime date) {
        this.user = user;
        this.message = message;
        this.date = date;
    }
}
