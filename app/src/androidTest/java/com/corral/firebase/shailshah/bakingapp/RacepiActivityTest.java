package com.corral.firebase.shailshah.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by shailshah on 11/5/17.
 */

@RunWith(AndroidJUnit4.class)
public class RacepiActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void scrollToItemBelowFold_checkItsText() {
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        click()));


        onView(withId(R.id.detail_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        click()));


        onView(withId(R.id.detail_description)).check(matches(withText("Recipe Introduction")));

    }
}
