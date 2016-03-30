package com.example.android.twitterclient;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import rx.functions.Action1;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> implements
        Action1<List<Tweet>> {

    private List<Tweet> tweets = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout view =
                (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.user.setText(tweets.get(position).user);
        holder.message.setText(tweets.get(position).message);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    @Override
    public void call(List<Tweet> tweets) {
        this.tweets.clear();
        this.tweets.addAll(tweets);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.user) public TextView user;
        @Bind(R.id.message) public TextView message;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}