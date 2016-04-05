package com.example.android.twitterclient.data;

import android.content.SharedPreferences;
import com.example.android.twitterclient.domain.User;
import javax.inject.Inject;

public class UserPersistence {
    public static final String KEY_USER = "user";
    private final SharedPreferences sharedPreferences;

    @Inject
    public UserPersistence(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void save(User user) {
        sharedPreferences.edit().putString(KEY_USER, user.name).apply();
    }

    public boolean exists() {
                    return sharedPreferences.getString(KEY_USER, null) != null;
    }

    public void clear() {
        sharedPreferences.edit().remove(KEY_USER).apply();
    }

    public User get() {
        return new User(sharedPreferences.getString(KEY_USER, null));
    }
}
