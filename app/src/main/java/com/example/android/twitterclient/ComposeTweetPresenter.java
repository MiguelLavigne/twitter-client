package com.example.android.twitterclient;

import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ComposeTweetPresenter extends BasePresenter<ComposeTweetView> {
    private final TweetGateway tweetGateway;

    @Inject
    public ComposeTweetPresenter(TweetGateway tweetGateway) {
        this.tweetGateway = tweetGateway;
    }

    public void onTweetClick() {
        String content = getView().getTweetMessage();
        if (content.length() > 0) {
            getView().setProgressVisible();
            tweetGateway.add(new Tweet("miguel", content))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aVoid -> getView().back(),
                            t -> getView().setProgressGone());
        }
    }

    public void onTweetContentChange(String content) {
        if (content.length() > 0) {
            getView().setTweetButtonEnabled();
        } else {
            getView().setTweetButtonDisabled();
        }
    }
}
