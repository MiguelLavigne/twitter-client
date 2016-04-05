package com.example.android.twitterclient.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.android.twitterclient.App;
import com.example.android.twitterclient.R;
import com.example.android.twitterclient.domain.TweetGateway;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import javax.inject.Inject;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TweetsView extends RelativeLayout {
    @Bind(R.id.tweets) RecyclerView tweets;

    @Inject TweetGateway tweetGateway;

    private TweetsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private Subscription subscription;

    public TweetsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((App) context.getApplicationContext()).component().inject(this);
        adapter = new TweetsAdapter();
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isInEditMode()) {
            return;
        }
        ButterKnife.bind(this);
        tweets.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        tweets.setHasFixedSize(true);
        tweets.setLayoutManager(layoutManager);
        tweets.setAdapter(adapter);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        subscription = tweetGateway.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter);
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
