package com.example.android.twitterclient.data;

import rx.functions.Func1;

public class LoginResponse {
    public static final Func1<LoginResponse, Boolean> SUCCESS = response -> response.successful;

    public final boolean successful;
    public final String username;

    public LoginResponse(boolean successful, String username) {
        this.successful = successful;
        this.username = username;
    }
}
