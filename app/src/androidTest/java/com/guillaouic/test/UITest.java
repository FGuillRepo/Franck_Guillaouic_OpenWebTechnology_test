package com.guillaouic.test;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers.*;

import com.guillaouic.test.activity.SearchActivity;
import com.guillaouic.test.fragment.Search_fragment;

import android.app.Activity;
import android.support.test.runner.AndroidJUnitRunner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class UITest extends AndroidJUnitRunner {

    public static final String STRING_TO_BE_TYPED = "Espresso";
    private static final Intent MY_ACTIVITY_INTENT = new Intent(InstrumentationRegistry.getTargetContext(), SearchActivity.class);

    /**
     * Use {@link ActivityTestRule} to create and launch the activity under test, and close it
     * after test completes. This is a replacement for {@link android.support.test.rule.ActivityTestRule;}.
     */
    @Rule
    public ActivityTestRule<SearchActivity> activityActivityTestRule = new ActivityTestRule<SearchActivity>(SearchActivity.class);

    @Before
    public void setup() {
        activityActivityTestRule.launchActivity(MY_ACTIVITY_INTENT);
    }


    @Test
    public void checkTextDisplayedInDynamicallyCreatedFragment() {
        Search_fragment fragment = new Search_fragment();
        activityActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_fragment, fragment).commit();
    }

    @Test
    public void changeText_sameActivity() {
        onView(withId(R.id.search_editText))
                .perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.search_button)).perform(click());
        }

    @Test
    public void Test_HistoryIntent() {
        onView(withId(R.id.action_history)).perform(click());
    }
}