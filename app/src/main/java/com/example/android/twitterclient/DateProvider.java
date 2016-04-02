package com.example.android.twitterclient;

import java.util.Date;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DateProvider {

    @Inject
    public DateProvider() {
    }

    public Date getTime() {
        return new Date();
    }
}
