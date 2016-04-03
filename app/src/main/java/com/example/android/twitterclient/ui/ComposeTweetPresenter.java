package com.example.android.twitterclient.ui;

import com.example.android.twitterclient.domain.CreateTweet;
import javax.inject.Inject;

public class ComposeTweetPresenter extends BasePresenter<ComposeTweetView> {
    private final CreateTweet createTweet;

    @Inject
    public ComposeTweetPresenter(CreateTweet createTweet) {
        this.createTweet = createTweet;
    }

    public void onTweetClick() {
        String content = getView().getTweetMessage();
        if (content.length() > 0) {
            getView().setProgressVisible();
            createTweet.execute(content)
                    .subscribe(
                            e -> getView().back(),
                            t -> {
                                showErrorMessage();
                                getView().setProgressGone();
                            });
        }
    }

    private void showErrorMessage() {
        getView().showErrorMessage();
    }

    public void onTweetContentChange(String content) {
        if (content.length() > 0) {
            getView().setTweetButtonEnabled();
        } else {
            getView().setTweetButtonDisabled();
        }
    }

    public void onRetryClick() {
        onTweetClick();
    }
}
