package com.example.android.twitterclient;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

@Singleton
public class TweetPersistence {
    private final Gson gson = new Gson();
    private final Type type = new TypeToken<List<Tweet>>() {}.getType();
    private final Preference<List<Tweet>> tweetsPreferences;

    @Inject
    public TweetPersistence(SharedPreferences sharedPreferences) {
        RxSharedPreferences sp = RxSharedPreferences.create(sharedPreferences);
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

    public void add(Tweet tweet) {
        List<Tweet> tweets = tweetsPreferences.get();
        assert tweets != null;
        tweets.add(tweet);
        tweetsPreferences.set(tweets);
    }

    public void addAll(List<Tweet> tweets) {
        List<Tweet> persisted = tweetsPreferences.get();
        assert persisted != null;
        persisted.addAll(tweets);
        tweetsPreferences.set(persisted);
    }

    public Observable<List<Tweet>> asObservable() {
        return tweetsPreferences.asObservable()
                .map(tweets -> {
                    Collections.sort(tweets, (lhs, rhs) -> lhs.date.compareTo(rhs.date));
                    Collections.reverse(tweets);
                    return tweets;
                });
    }

    public void clear() {
        tweetsPreferences.delete();
    }
}