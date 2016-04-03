package com.example.android.twitterclient.util;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

@Singleton
public class DateProvider {

    @Inject
    public DateProvider() {
    }

    public DateTime getTime() {
        return new DateTime(DateTimeZone.UTC);
    }

    public DateTime fromStringDate(String date) {
        if (date == null) {
            return null;
        }
        return DateTime.parse(date);
    }
}
