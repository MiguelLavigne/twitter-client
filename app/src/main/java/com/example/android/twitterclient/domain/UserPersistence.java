package com.example.android.twitterclient.domain;

public interface UserPersistence {
    void save(User user);

    boolean exists();

    void clear();

    User get();
}
