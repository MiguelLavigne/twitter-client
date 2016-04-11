package com.example.android.twitterclient.ui;

import com.example.android.twitterclient.domain.Tweet;
import com.example.android.twitterclient.domain.TweetGateway;
import com.example.android.twitterclient.domain.UserPersistence;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class TweetsPresenter extends BasePresenter<TweetsView> {
    private final TweetGateway tweetGateway;
    private final UserPersistence userPersistence;
    private final TweetTimeFormatter tweetTimeFormatter;

    private CompositeSubscription subscriptions;

    @Inject
    public TweetsPresenter(TweetGateway tweetGateway, UserPersistence userPersistence, TweetTimeFormatter tweetTimeFormatter) {
        this.tweetGateway = tweetGateway;
        this.userPersistence = userPersistence;
        this.tweetTimeFormatter = tweetTimeFormatter;
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
                        .flatMap(this::convertToTweetViewItem)
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

    private Observable<List<TweetViewItem>> convertToTweetViewItem(List<Tweet> tweets) {
        return Observable.from(tweets)
                .map(from -> new TweetViewItem(from.user, from.message, tweetTimeFormatter.format(from.date)))
                .toList();
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
