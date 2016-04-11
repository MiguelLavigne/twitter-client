package com.example.android.twitterclient.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.android.twitterclient.R;
import java.util.ArrayList;
import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    private List<TweetViewItem> tweets = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.user.setText(tweets.get(position).name);
        holder.message.setText(tweets.get(position).message);
        holder.date.setText(tweets.get(position).since);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void setItems(List<TweetViewItem> tweets) {
        this.tweets.clear();
        this.tweets.addAll(tweets);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.user) public TextView user;
        @Bind(R.id.message) public TextView message;
        @Bind(R.id.date) public TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}