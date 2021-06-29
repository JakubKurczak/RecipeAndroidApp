package com.example.recipies;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import models.User;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class AddRecipieTest {

    @Rule
    public ActivityScenarioRule<AddRecipie> mainActivityActivityTestRule =
            new ActivityScenarioRule<>(AddRecipie.class);

    @Test
    public void validateAddingNewCorrectRecipie(){
        User.setDoc_id("ignacy_jan");

        onView(ViewMatchers.withId(R.id.input_recipie_name)).perform(typeText("Szakszuka"));
        onView(ViewMatchers.withId(R.id.quantity)).perform(typeText("1"));
        onView(withId(R.id.ingredient_unit)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("q"))).perform(click());
        onView(withId(R.id.add_recipie_scroll_view)).perform(swipeUp());
        onView(withId(R.id.add_recipie_scroll_view)).perform(swipeUp());
        onView(withId(R.id.text_description)).perform(typeText("Przepis przepis przepis"));
        onView(ViewMatchers.withId(R.id.finish)).check(matches(isEnabled()));

    }


    @Test
    public void validateAddingNewEmptyRecipie(){
        User.setDoc_id("ignacy_jan");

        onView(ViewMatchers.withId(R.id.containedButton)).perform(click());
        onView(ViewMatchers.withId(R.id.containedButton)).perform(click());
        onView(ViewMatchers.withId(R.id.containedButton)).perform(click());
        onView(withId(R.id.add_recipie_scroll_view)).perform(swipeUp());
        onView(withId(R.id.add_recipie_scroll_view)).perform(swipeUp());
        onView(withId(R.id.add_recipie_scroll_view)).perform(swipeUp());
        onView(ViewMatchers.withId(R.id.add_step)).perform(click());
        onView(withId(R.id.add_recipie_scroll_view)).perform(swipeUp());
        onView(withId(R.id.add_recipie_scroll_view)).perform(swipeUp());
        onView(ViewMatchers.withId(R.id.finish)).check(matches(isEnabled()));

    }


}
