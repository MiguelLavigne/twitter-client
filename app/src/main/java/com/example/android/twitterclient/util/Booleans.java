package com.example.android.twitterclient.util;

import rx.functions.Func1;

public class Booleans {
    private Booleans() {
        throw new AssertionError("use statically");
    }

    public static final Func1<Boolean, Boolean> TRUE = result -> result;
}
