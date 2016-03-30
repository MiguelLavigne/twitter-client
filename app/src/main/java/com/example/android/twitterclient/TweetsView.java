package com.example.android.twitterclient;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import javax.inject.Inject;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class TweetsView extends RelativeLayout {
    @Bind(R.id.tweets) RecyclerView tweets;

    @Inject TweetRepository tweetRepository;

    private TweetsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private Subscription subscription;

    public TweetsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((App) context.getApplicationContext()).component().inject(this);
        adapter = new TweetsAdapter();
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isInEditMode()) {
            return;
        }
        ButterKnife.bind(this);
        tweets.setHasFixedSize(true);
        tweets.setLayoutManager(layoutManager);
        tweets.setAdapter(adapter);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        subscription = tweetRepository.get().observeOn(AndroidSchedulers.mainThread()).subscribe(adapter);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        subscription.unsubscribe();
    }

    @OnClick(R.id.add_tweet)
    public void onAddTweetClicked() {
        getContext().startActivity(new Intent(getContext(), ComposeTweetActivity.class));
    }
}
