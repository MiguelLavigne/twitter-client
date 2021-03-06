package com.example.android.twitterclient.util;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import rx.Observable;
import rx.functions.Action1;

public interface Preference<T> {
    /** The key for which this preference will store and retrieve values. */
    @NonNull
    String key();

    /** The value used if none is stored. May be {@code null}. */
    @Nullable
    T defaultValue();

    /**
     * Retrieve the current value for this preference. Returns {@link #defaultValue()} if no value is
     * set.
     */
    @Nullable
    T get();

    /**
     * Change this preference's stored value to {@code value}. A value of {@code null} will delete the
     * preference.
     */
    void set(@Nullable T value);

    /** Returns true if this preference has a stored value. */
    boolean isSet();

    /** Delete the stored value for this preference, if any. */
    void delete();

    /**
     * Observe changes to this preference. The current value or {@link #defaultValue()} will be
     * emitted on first subscribe.
     */
    @CheckResult
    @NonNull
    Observable<T> asObservable();

    /**
     * An action which stores a new value for this preference. Passing {@code null} will delete the
     * preference.
     */
    @CheckResult
    @NonNull
    Action1<? super T> asAction();
}
