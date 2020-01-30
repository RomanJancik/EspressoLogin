package com.example.espressologin;

import android.content.Intent;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.espressologin.ui.login.LoginActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertTrue;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;


@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public final IntentsTestRule<LoginActivity> loginActivity = new IntentsTestRule<>(LoginActivity.class, false, false);
    //ustala jakie activity testujemy, IntentsTestRule pozwala przechodzić pomiędzy activity w teście
    // false oznacza że activity nie startuje automatycznie


    @Test
    public void loginTest() {

        loginActivity.launchActivity(new Intent());

        setUserDataAndCallLogin("test@test.com", "secretpassword");
        waitFor(150);
        onView(withText(R.string.welcome)).inRoot(withDecorView(not(is(loginActivity.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        onView((withId(R.id.login))).check(matches(isEnabled()));

        setUserDataAndCallLogin("wrong_email@test.com", "wrongpassword");
        waitFor(150);
        onView(withText(R.string.login_failed)).inRoot(withDecorView(not(is(loginActivity.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

        setUserDataAndCallLogin("not_email", "secretpassword");
        waitFor(150);
        onView(withText(R.string.login_failed)).inRoot(withDecorView(not(is(loginActivity.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));


        setUserDataAndCallLogin("test@test.com", "short");
        waitFor(150);
        onView(withId(R.id.password)).check(matches(hasErrorText(loginActivity.getActivity().getString(R.string.invalid_password))));


        setUserDataAndCallLogin("test@test.com", "");
        waitFor(150);
        onView(withId(R.id.login)).check(matches(not(isEnabled())));

        setUserDataAndCallLogin("", "secretpassword");
        waitFor(150);
        onView(withId(R.id.login)).check(matches(not(isEnabled())));
    }

    private void setUserDataAndCallLogin(String username, String password) {
        onView(withId(R.id.username))// pod pole username podstawiamy tekst
                .perform(replaceText(username));

        onView(withId(R.id.password))// pod pole hasło podstawiamy tekst
                .perform(replaceText(password));

        onView(withId(R.id.login))// klikamy zaloguj
                .perform(click());
    }

    private void waitFor(Integer millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}