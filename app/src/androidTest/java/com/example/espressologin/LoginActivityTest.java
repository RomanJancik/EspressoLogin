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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;



@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public final IntentsTestRule<LoginActivity> loginActivity = new IntentsTestRule<>(LoginActivity.class, false, false);
    //ustala jakie activity testujemy, IntentsTestRule pozwala przechodzić pomiędzy activity w teście
    // false oznacza że activity nie startuje automatycznie


    @Test
    public void loginTest() {

        loginActivity.launchActivity(new Intent());

        onView(withId(R.id.username))// pod pole username podstawiamy tekst
                .perform(replaceText("test@test.com"));

        onView(withId(R.id.password))// pod pole hasło podstawiamy tekst
                .perform(replaceText("secretpassword"));

        onView(withId(R.id.login))// klikamy zaloguj
                .perform(click());

        waitFor();

        assertTrue(loginActivity.getActivity().isDestroyed());//sprawdzamy czy aplikacja sie zamknęła


    }

    private void waitFor() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
