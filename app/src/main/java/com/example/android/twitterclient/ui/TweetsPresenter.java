package com.example.android.twitterclient.ui;

import com.example.android.twitterclient.domain.TweetGateway;
import com.example.android.twitterclient.domain.UserPersistence;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class TweetsPresenter extends BasePresenter<TweetsView> {
    private final TweetGateway tweetGateway;
    private final UserPersistence userPersistence;
    private CompositeSubscription subscriptions;

    @Inject
    public TweetsPresenter(TweetGateway tweetGateway, UserPersistence userPersistence) {
        this.tweetGateway = tweetGateway;
        this.userPersistence = userPersistence;
    }

    @Override
    protected void onReady() {
        super.onReady();
        getView().setTitle("@" + userPersistence.get().name);
        subscriptions = new CompositeSubscription();
        subscriptions.add(
                tweetGateway.get()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getView()::setTweets)
        );
        subscriptions.add(
                tweetGateway.refreshingStateObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(refreshState -> {
                            getView().setRefreshing(!refreshState.isDone());
                            if (!refreshState.isSuccessful()) {
                                getView().showErrorWithRetry();
                            }
                        })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
    }

    public void refresh() {
        tweetGateway.forceRefresh();
    }
}
