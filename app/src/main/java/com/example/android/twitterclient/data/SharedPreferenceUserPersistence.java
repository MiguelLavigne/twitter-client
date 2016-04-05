package com.example.android.twitterclient.data;

import android.content.SharedPreferences;
import com.example.android.twitterclient.domain.User;
import com.example.android.twitterclient.domain.UserPersistence;
import javax.inject.Inject;

public class SharedPreferenceUserPersistence implements UserPersistence {
    public static final String KEY_USER = "user";
    private final SharedPreferences sharedPreferences;

    @Inject
    public SharedPreferenceUserPersistence(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void save(User user) {
        sharedPreferences.edit().putString(KEY_USER, user.name).apply();
    }

    @Override
    public boolean exists() {
        return sharedPreferences.getString(KEY_USER, null) != null;
    }

    @Override
    public void clear() {
        sharedPreferences.edit().remove(KEY_USER).apply();
    }

    @Override
    public User get() {
        return new User(sharedPreferences.getString(KEY_USER, null));
    }
}
