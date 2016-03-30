package com.example.android.twitterclient;

import javax.inject.Inject;

public class ComposeTweetPresenter extends BasePresenter<ComposeTweetView> {
    private final TweetRepository tweetRepository;

    @Inject
    public ComposeTweetPresenter(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public void onTweetClick() {
        String content = getView().getTweetMessage();
        if (content.length() > 0) {
            tweetRepository.save(new Tweet("miguel", content));
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
