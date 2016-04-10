package com.example.android.twitterclient.util;

import com.example.android.twitterclient.domain.DateProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

@Singleton
public class DateProviderImpl implements DateProvider {

    @Inject
    public DateProviderImpl() {
    }

    @Override
    public DateTime getTime() {
        return new DateTime(DateTimeZone.UTC);
    }

    @Override
    public DateTime fromStringDate(String date) {
        if (date == null) {
            return null;
        }
        return DateTime.parse(date);
    }
}
