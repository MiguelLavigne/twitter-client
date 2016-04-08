package com.example.android.twitterclient.ui;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.android.twitterclient.App;
import com.example.android.twitterclient.R;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import javax.inject.Inject;
import rx.subscriptions.CompositeSubscription;

public class ComposeTweetView extends FrameLayout implements BaseView {
    @Bind(R.id.coordinator) CoordinatorLayout coordinator;
    @Bind(R.id.tweet_msg) EditText msgEdit;
    @Bind(R.id.tweet) Button tweetButton;
    @Bind(R.id.progress_container) FrameLayout progress;

    @Inject ComposeTweetPresenter presenter;

    private CompositeSubscription subscriptions;

    public ComposeTweetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((App) context.getApplicationContext()).component().inject(this);
        subscriptions = new CompositeSubscription();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);
        subscriptions.add(
                RxTextView.afterTextChangeEvents(msgEdit).subscribe(textViewAfterTextChangeEvent -> {
                    presenter.onTweetContentChange(textViewAfterTextChangeEvent.editable().toString());
                }));
        subscriptions.add(
                RxView.clicks(tweetButton).subscribe(onClickEvent -> {
                    presenter.onTweetClick();
                }));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        subscriptions.unsubscribe();
        presenter.dropView(this);
    }

    public void setTweetButtonDisabled() {
        tweetButton.setEnabled(false);
    }

    public void setTweetButtonEnabled() {
        tweetButton.setEnabled(true);
    }

    public void setProgressVisible() {
        progress.setVisibility(View.VISIBLE);
    }

    public void setProgressGone() {
        progress.setVisibility(View.GONE);
    }

    public String getTweetMessage() {
        return msgEdit.getText().toString();
    }

    public void back() {
        ((Activity) getContext()).finish();
    }

    public void showErrorMessage() {
        Snackbar snackbar = Snackbar.make(coordinator, "Failed to tweet", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Retry", v -> {
            presenter.onRetryClick();
        });
    }
}
