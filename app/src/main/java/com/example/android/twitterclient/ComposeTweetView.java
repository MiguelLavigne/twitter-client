package com.example.android.twitterclient;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import javax.inject.Inject;
import rx.subscriptions.CompositeSubscription;

public class ComposeTweetView extends RelativeLayout implements BaseView {
    @Bind(R.id.tweet_msg) EditText msgEdit;
    @Bind(R.id.tweet) Button tweetButton;

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

    public String getTweetMessage() {
        return msgEdit.getText().toString();
    }

    public void back() {
        ((Activity) getContext()).finish();
    }
}
