package com.example.android.twitterclient.ui;

import com.example.android.twitterclient.domain.DateProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.joda.time.DateTime;
import org.joda.time.Duration;

@Singleton
public class TweetTimeFormatter {
    private final DateProvider dateProvider;

    @Inject
    public TweetTimeFormatter(DateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }

    public String format(DateTime time) {
        DateTime now = dateProvider.getTime();
        Duration duration = new Duration(time, now);
        if (duration.getStandardMinutes() == 0) {
            return "<1m";
        } else if (duration.getStandardHours() == 0) {
            return duration.getStandardMinutes() + "m";
        } else if (duration.getStandardDays() == 0) {
            return duration.getStandardHours() + "h";
        }
        return duration.getStandardDays() + "d";  // We'll only support up to days for now
    }
}
