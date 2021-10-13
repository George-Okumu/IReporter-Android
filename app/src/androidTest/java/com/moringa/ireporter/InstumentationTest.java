package com.moringa.ireporter;

import com.moringa.ireporter.ui.CreateRedActivity;
import com.moringa.ireporter.ui.SignUpActivity;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class InstumentationTest {
        @Rule
        public ActivityScenarioRule<SignUpActivity> mActivityRule =
                new ActivityScenarioRule<>(SignUpActivity.class);

        @Test
    public void  validateEditText() {
            onView(withId(R.id.signupname)).perform(typeText("Brian"))
                    .check(matches(withText("Brian")));
        }

    @Test
    public void  validateEditTextInput() {
        onView(withId(R.id.signupemail)).perform(typeText("brayokarush@gmail.com"))
                .check(matches(withText("brayokarush@gmail.com")));
    }
}
