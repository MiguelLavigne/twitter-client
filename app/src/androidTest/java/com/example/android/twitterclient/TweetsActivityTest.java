package com.example.android.twitterclient;

import android.preference.PreferenceManager;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.android.twitterclient.ui.TweetsActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class TweetsActivityTest {

    @Rule public ActivityTestRule<TweetsActivity> activityRule =
            new ActivityTestRule<>(TweetsActivity.class);

    @Before
    public void setUp() throws Exception {
        loginIn();
    }

    @After
    public void tearDown() throws Exception {
        logout();
    }

    @Test
    public void clickingOnPlusFabLaunchesComposeActivity() throws Exception {
        onView(withId(R.id.add_tweet)).perform(click());
        onView(withText(R.string.compose)).check(matches(isDisplayed()));
    }

    private void loginIn() {
        PreferenceManager.getDefaultSharedPreferences(activityRule.getActivity()).edit().clear().commit();
        onView(withId(R.id.username)).perform(typeText("joeblo"));
        onView(withId(R.id.password)).perform(typeText("trov22"));
        onView(withId(R.id.login)).perform(click());
    }

    private void logout() {
        PreferenceManager.getDefaultSharedPreferences(activityRule.getActivity()).edit().clear().commit();
    }
}
