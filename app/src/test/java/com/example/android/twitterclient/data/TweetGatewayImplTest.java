package com.example.android.twitterclient.data;

import com.example.android.twitterclient.TestRunner;
import com.example.android.twitterclient.domain.GetTweetResponse;
import com.example.android.twitterclient.domain.Tweet;
import com.example.android.twitterclient.domain.TweetPersistence;
import com.example.android.twitterclient.domain.TwitterApi;
import com.example.android.twitterclient.util.Preference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestObserver;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(TestRunner.class)
public class TweetGatewayImplTest {

    private static DateTime NOW = DateTime.now();
    private static String TIMESTAMP = NOW.toString();
    private static String TIMESTAMP_SAVED = NOW.minusHours(1).toString();
    private static List<Tweet> TWEETS;

    static {
        TWEETS = new ArrayList<>(2);
        TWEETS.add(new Tweet("user1", "message1", DateTime.now()));
        TWEETS.add(new Tweet("user2", "message2", DateTime.now()));
    }

    @Mock private TwitterApi twitterApi;
    @Mock private TweetPersistence tweetPersistence;
    @Mock private Preference<String> since;

    @InjectMocks private TweetGatewayImpl sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnLocalTweets() throws Exception {
        when(twitterApi.getTweets(anyString())).thenReturn(Observable.empty());
        when(tweetPersistence.asObservable()).thenReturn(Observable.just(TWEETS));

        TestObserver<List<Tweet>> observer = new TestObserver<>();
        sut.get().subscribe(observer);

        observer.assertReceivedOnNext(Collections.singletonList(TWEETS));
    }

    @Test
    public void shouldGetRemoteTweets() throws Exception {
        when(twitterApi.getTweets(anyString())).thenReturn(successfulResponse());
        when(tweetPersistence.asObservable()).thenReturn(Observable.just(TWEETS));

        sut.get().subscribe();

        verify(twitterApi).getTweets(anyString());
    }

    @Test
    public void shouldSaveRemoteTweets() throws Exception {
        when(twitterApi.getTweets(anyString())).thenReturn(successfulResponse());
        when(tweetPersistence.asObservable()).thenReturn(Observable.just(TWEETS));

        sut.get().subscribe();

        verify(tweetPersistence).addAll(TWEETS);
    }

    @Test
    public void shouldSaveNewTimestamp() throws Exception {
        when(twitterApi.getTweets(anyString())).thenReturn(successfulResponse());
        when(tweetPersistence.asObservable()).thenReturn(Observable.empty());

        sut.get().subscribe();

        verify(since).set(TIMESTAMP);
    }

    @Test
    public void shouldUseSavedTimestampWhenRequestingRemoteTweets() throws Exception {
        when(twitterApi.getTweets(anyString())).thenReturn(Observable.empty());
        when(tweetPersistence.asObservable()).thenReturn(Observable.empty());
        when(since.get()).thenReturn(TIMESTAMP_SAVED);

        sut.get().subscribe();

        verify(twitterApi).getTweets(TIMESTAMP_SAVED);
    }

    private Observable<GetTweetResponse> successfulResponse() {
        return Observable.just(new GetTweetResponse(TWEETS, TIMESTAMP));
    }
}