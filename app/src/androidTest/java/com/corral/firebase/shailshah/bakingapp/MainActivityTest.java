package com.corral.firebase.shailshah.bakingapp;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by shailshah on 11/5/17.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void scrollToItemBelowFold_checkItsText() {
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        click()));

    }
    @Test
    public void scrollToItemPositionTest()
    {
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.scrollToPosition(1));
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.scrollToPosition(2));
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.scrollToPosition(3));
        onView(withId(R.id.item_list))
                .perform(RecyclerViewActions.scrollToPosition(4));
    }


}