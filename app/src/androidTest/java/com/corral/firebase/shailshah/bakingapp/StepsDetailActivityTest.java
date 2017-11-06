package com.corral.firebase.shailshah.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by shailshah on 11/5/17.
 */

@RunWith(AndroidJUnit4.class)
public class StepsDetailActivityTest {
    private static String name = "Shail";

    @Rule
    public IntentsTestRule<StepsDetailAcitvity> mActivityRule = new IntentsTestRule<>(
            StepsDetailAcitvity.class);


    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void click_sendName_Button() {

        onView(withId(R.id.next_button)).perform(click());
        // intended(Matcher<Intent> matcher) asserts the given matcher matches one and only one
        // intent sent by the application.
        intended(allOf(
                hasExtra(Intent.ACTION_DEFAULT, name)));


        intended(allOf(hasExtra("Position",1)));
        intended(allOf(hasExtra("Position",2)));

    }

}
