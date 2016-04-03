package com.example.android.twitterclient.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.example.android.twitterclient.App;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

public class ConnectivityProvider {
    private ConnectivityManager connectivityManager;
    private Context context;

    @Inject
    public ConnectivityProvider(App app, ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
        context = app;
    }

    public boolean isConnected() {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public Observable<Boolean> observe() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                final BroadcastReceiver receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(isConnected());
                        }
                    }
                };
                context.registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
                subscriber.add(new MainThreadSubscription() {
                    @Override
                    protected void onUnsubscribe() {
                        context.unregisterReceiver(receiver);
                    }
                });
                subscriber.onNext(isConnected());
            }
        });
    }
}
