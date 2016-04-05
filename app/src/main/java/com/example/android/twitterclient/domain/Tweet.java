package com.example.android.twitterclient.domain;

import com.example.android.twitterclient.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tweet other = (Tweet) o;
        return Objects.equals(user, other.user) &&
                Objects.equals(message, other.message) &&
                Objects.equals(date, other.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, message, date);
    }
}
