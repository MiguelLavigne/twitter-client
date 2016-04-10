package com.example.android.twitterclient.domain;

import org.joda.time.DateTime;

public interface DateProvider {
    DateTime getTime();

    DateTime fromStringDate(String date);
}
