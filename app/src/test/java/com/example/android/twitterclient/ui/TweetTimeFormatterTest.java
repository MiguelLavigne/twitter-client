package com.example.android.twitterclient.ui;

import com.example.android.twitterclient.domain.DateProvider;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TweetTimeFormatterTest {
    @Mock DateProvider dateProvider;
    @InjectMocks TweetTimeFormatter sut;

    DateTime time = DateTime.now().withSecondOfMinute(0).withMillisOfSecond(0);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void lessThanOneMinutes() throws Exception {
        Mockito.when(dateProvider.getTime()).thenReturn(time);

        String formatter = sut.format(time.minusSeconds(59));

        assertThat(formatter, is("<1m"));
    }

    @Test
    public void betweenOneAndTwoMinutes() throws Exception {
        Mockito.when(dateProvider.getTime()).thenReturn(time);

        String formatter = sut.format(time.minusMinutes(1));

        assertThat(formatter, is("1m"));
    }

    @Test
    public void betweenTwoAndThreeMinutes() throws Exception {
        Mockito.when(dateProvider.getTime()).thenReturn(time);

        String formatter = sut.format(time.minusMinutes(2));

        assertThat(formatter, is("2m"));
    }

    @Test
    public void betweenOneAndTwoHours() throws Exception {
        Mockito.when(dateProvider.getTime()).thenReturn(time);

        String formatter = sut.format(time.minusHours(1));

        assertThat(formatter, is("1h"));
    }

    @Test
    public void betweenTwoAndThreeHours() throws Exception {
        Mockito.when(dateProvider.getTime()).thenReturn(time);

        String formatter = sut.format(time.minusHours(2));

        assertThat(formatter, is("2h"));
    }

    @Test
    public void oneDay() throws Exception {
        Mockito.when(dateProvider.getTime()).thenReturn(time);

        String formatter = sut.format(time.minusDays(1));

        assertThat(formatter, is("1d"));
    }
}