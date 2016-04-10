package com.example.android.twitterclient;

import android.content.SharedPreferences;
import com.example.android.twitterclient.data.TwitterApiPersistence;
import com.example.android.twitterclient.domain.DateProvider;
import com.example.android.twitterclient.domain.GetTweetResponse;
import com.example.android.twitterclient.domain.LoginResponse;
import com.example.android.twitterclient.domain.Tweet;
import com.example.android.twitterclient.domain.TwitterApi;
import dagger.Module;
import dagger.Provides;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import org.joda.time.DateTime;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;
import rx.Observable;

@Module
public class MockNetModule {

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://mock")
                .build();
    }

    @Provides
    @Singleton
    MockRetrofit provideMockRetrofit(Retrofit retrofit, SharedPreferences sp) {
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setDelay(sp.getInt("mock_request_delay", 2), TimeUnit.SECONDS);
        behavior.setFailurePercent(sp.getInt("mock_failure_percent", 0));
        return new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
    }

    @Provides
    @Singleton
    public TwitterApi provideTwitterApi(MockRetrofit mockRetrofit, TwitterApiPersistence persistence, DateProvider dateProvider) {
        return new MockTwitterApi(mockRetrofit.create(TwitterApi.class), persistence, dateProvider);
    }

    /**
     * Simple class which allows us to fake the behavior of the {@link TwitterApi}
     */
    private static class MockTwitterApi implements TwitterApi {
        private final BehaviorDelegate<TwitterApi> behaviorDelegate;
        private final TwitterApiPersistence persistence;
        private final DateProvider dateProvider;

        public MockTwitterApi(BehaviorDelegate<TwitterApi> behaviorDelegate, TwitterApiPersistence persistence, DateProvider dateProvider) {
            this.behaviorDelegate = behaviorDelegate;
            this.persistence = persistence;
            this.dateProvider = dateProvider;
        }

        @Override
        public Observable<LoginResponse> login(String username, String password) {
            final boolean successful = "trov22".equals(password);
            return behaviorDelegate.returningResponse(new LoginResponse(successful, username)).login(username, password);
        }

        @Override
        public Observable<Tweet> postTweet(Tweet tweet) {
            return behaviorDelegate.returningResponse(tweet).postTweet(tweet)
                    .doOnNext(persistence::add);
        }

        @Override
        public Observable<GetTweetResponse> getTweets(String since) {
            final List<Tweet> tweets = persistence.getAll().toBlocking().first();
            final GetTweetResponse response = new GetTweetResponse(getTweetsSince(tweets, since), dateProvider.getTime().toString());

            return behaviorDelegate.returningResponse(response).getTweets(since);
        }

        private List<Tweet> getTweetsSince(List<Tweet> tweets, String sinceString) {
            DateTime since = dateProvider.fromStringDate(sinceString);
            return Observable.from(tweets)
                    .filter(tweet -> since == null || tweet.date.isAfter(since))
                    .toList()
                    .toBlocking()
                    .first();
        }
    }
}
