package com.example.android.twitterclient.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import rx.Observable;
import rx.functions.Action1;

public class PreferenceDelegate<T> implements Preference<T> {
    private com.f2prateek.rx.preferences.Preference<T> delegate;

    public PreferenceDelegate(com.f2prateek.rx.preferences.Preference<T> delegate) {
        this.delegate = delegate;
    }

    @NonNull
    @Override
    public String key() {
        return delegate.key();
    }

    @Nullable
    @Override
    public T defaultValue() {
        return delegate.defaultValue();
    }

    @Nullable
    @Override
    public T get() {
        return delegate.get();
    }

    @Override
    public void set(@Nullable T value) {
        delegate.set(value);
    }

    @Override
    public boolean isSet() {
        return delegate.isSet();
    }

    @Override
    public void delete() {
        delegate.delete();
    }

    @NonNull
    @Override
    public Observable<T> asObservable() {
        return delegate.asObservable();
    }

    @NonNull
    @Override
    public Action1<? super T> asAction() {
        return delegate.asAction();
    }
}
