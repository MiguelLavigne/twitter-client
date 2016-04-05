package com.example.android.twitterclient.util;

import java.util.Arrays;

/**
 * Copied from java.util.Objects to make it available
 * for older api 17;
 */
public class Objects {
    private Objects() {
        throw new AssertionError("use statically");
    }

    /**
     * Null-safe equivalent of {@code a.equals(b)}.
     */
    public static boolean equals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }

    /**
     * Convenience wrapper for {@link Arrays#hashCode}, adding varargs.
     * This can be used to compute a hash code for an object's fields as follows:
     * {@code Objects.hash(a, b, c)}.
     */
    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }
}
