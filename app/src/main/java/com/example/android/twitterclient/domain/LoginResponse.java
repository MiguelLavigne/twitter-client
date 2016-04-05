package com.example.android.twitterclient.domain;

public class LoginResponse {
    public final boolean successful;
    public final String username;

    public LoginResponse(boolean successful, String username) {
        this.successful = successful;
        this.username = username;
    }
}
