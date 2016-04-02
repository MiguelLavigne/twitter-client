package com.example.android.twitterclient;

import dagger.Module;
import dagger.Provides;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;
import rx.Observable;

@Module
public class NetModule {

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://some-twitter-url-goes-here.com")
                .build();
    }

    @Provides
    @Singleton
    MockRetrofit provideMockRetrofit(Retrofit retrofit) {
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setFailurePercent(0);
        return new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
    }

    @Provides
    @Singleton
    public TwitterApi provideTwitterApi(MockRetrofit mockRetrofit, TwitterApiPersistence persistence) {
        return new MockTwitterApi(mockRetrofit.create(TwitterApi.class), persistence);
    }

    private static class MockTwitterApi implements TwitterApi {
        private final BehaviorDelegate<TwitterApi> behaviorDelegate;
        private final TwitterApiPersistence persistence;

        public MockTwitterApi(BehaviorDelegate<TwitterApi> behaviorDelegate, TwitterApiPersistence persistence) {
            this.behaviorDelegate = behaviorDelegate;
            this.persistence = persistence;
        }

        @Override
        public Observable<Tweet> postTweet(Tweet tweet) {
            return behaviorDelegate.returningResponse(tweet).postTweet(tweet);
        }

        @Override
        public Observable<List<Tweet>> getTweets() {
            List<Tweet> tweets = new ArrayList<>(2);
            tweets.add(new Tweet("user6", "fetch tweet message"));
            tweets.add(new Tweet("user7", "fetch tweet message"));
            return behaviorDelegate.returningResponse(tweets).getTweets();
        }
    }
}
