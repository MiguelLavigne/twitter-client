package com.example.android.twitterclient.data;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import com.example.android.twitterclient.domain.Tweet;
import com.example.android.twitterclient.domain.TweetPersistence;
import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;
import rx.Observable;

public class SharedPreferenceTweetPersistence implements TweetPersistence {
    private final Gson gson;
    private final Type type = new TypeToken<List<Tweet>>() {}.getType();
    private final Preference<List<Tweet>> tweetsPreferences;

    @Inject
    public SharedPreferenceTweetPersistence(RxSharedPreferences sp) {
        gson = buildGson();
        tweetsPreferences = sp.getObject("tweets", new ArrayList<>(), new Preference.Adapter<List<Tweet>>() {
            @Override
            public List<Tweet> get(@NonNull String key, @NonNull SharedPreferences preferences) {
                return fromJson(preferences.getString(key, "[]"));
            }

            @Override
            public void set(@NonNull String key, @NonNull List<Tweet> value, @NonNull SharedPreferences.Editor editor) {
                editor.putString(key, toJson(value));
            }

            private String toJson(List<Tweet> tweets) {
                return gson.toJson(tweets, type);
            }

            private List<Tweet> fromJson(String str) {
                return gson.fromJson(str, type);
            }
        });
    }

    @NonNull
    private Gson buildGson() {
        return new GsonBuilder().registerTypeAdapter(DateTime.class, new TypeAdapter<DateTime>() {
            @Override
            public void write(JsonWriter out, DateTime value) throws IOException {
                out.value(value.toString());
            }

            @Override
            public DateTime read(JsonReader in) throws IOException {
                return DateTime.parse(in.nextString());
            }
        }).create();
    }

    @Override
    public void addAll(List<Tweet> tweets) {
        List<Tweet> persistedTweets = tweetsPreferences.get();
        assert persistedTweets != null;
        for (Tweet tweet : tweets) {
            internalAdd(tweet, persistedTweets);
        }
        tweetsPreferences.set(persistedTweets);
    }

    @Override
    public Observable<List<Tweet>> asObservable() {
        return tweetsPreferences.asObservable()
                .map(tweets -> {
                    Collections.sort(tweets, (lhs, rhs) -> lhs.date.compareTo(rhs.date));
                    Collections.reverse(tweets);
                    return tweets;
                });
    }

    @Override
    public void clear() {
        tweetsPreferences.delete();
    }

    @Override
    public void add(Tweet tweet) {
        List<Tweet> persistedTweets = tweetsPreferences.get();
        assert persistedTweets != null;
        internalAdd(tweet, persistedTweets);
        tweetsPreferences.set(persistedTweets);
    }

    private void internalAdd(Tweet tweet, List<Tweet> tweets) {
        if (!tweets.contains(tweet)) {
            tweets.add(tweet);
        }
    }
}