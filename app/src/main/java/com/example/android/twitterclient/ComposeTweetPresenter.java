package com.example.android.twitterclient;

import javax.inject.Inject;

public class ComposeTweetPresenter extends BasePresenter<ComposeTweetView> {
    private final TweetGateway tweetGateway;

    @Inject
    public ComposeTweetPresenter(TweetGateway tweetGateway) {
        this.tweetGateway = tweetGateway;
    }

    public void onTweetClick() {
        String content = getView().getTweetMessage();
        if (content.length() > 0) {
            tweetGateway.add(new Tweet("miguel", content));
            getView().back();
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
