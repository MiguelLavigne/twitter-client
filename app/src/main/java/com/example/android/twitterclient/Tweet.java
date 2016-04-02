package com.example.android.twitterclient;

import java.util.Date;

public class Tweet {
    public final String user;
    public final String message;
    public final Date date;

    public Tweet(String user, String message, Date date) {
        this.user = user;
        this.message = message;
        this.date = date;
    }
}
