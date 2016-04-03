package com.example.android.twitterclient.util;

import rx.functions.Func1;

public class Funcs {
    private Funcs() {
        throw new AssertionError("use statically");
    }

    public static <T> Func1<T, Boolean> not(final Func1<T, Boolean> func) {
        return value -> !func.call(value);
    }
}
