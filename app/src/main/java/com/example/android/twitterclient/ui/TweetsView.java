package com.example.android.twitterclient.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.android.twitterclient.App;
import com.example.android.twitterclient.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import java.util.List;
import javax.inject.Inject;

public class TweetsView extends FrameLayout implements BaseView {
    @Bind(R.id.tweets) RecyclerView tweets;
    @Bind(R.id.refresh) SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.add_tweet) FloatingActionButton composeTweet;

    @Inject TweetsPresenter presenter;

    private TweetsAdapter adapter;
    private LinearLayoutManager layoutManager;

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
        swipeRefresh.setOnRefreshListener(presenter::refresh);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);
    }

    @OnClick(R.id.add_tweet)
    public void onAddTweetClicked() {
        getContext().startActivity(new Intent(getContext(), ComposeTweetActivity.class));
    }

    public void setTweets(List<TweetViewItem> tweets) {
        adapter.setItems(tweets);
    }

    public void setRefreshing(boolean refreshing) {
        swipeRefresh.setRefreshing(refreshing);
    }

    public void showErrorWithRetry() {
        Snackbar bar = Snackbar.make(composeTweet, R.string.failed_to_refresh, Snackbar.LENGTH_LONG);
        bar.setAction(R.string.retry, view -> presenter.refresh());
        bar.show();
    }

    public void setTitle(String title) {
        ((Activity) getContext()).setTitle(title);
    }
}
