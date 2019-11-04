package com.abb.bakingapp;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RecipeListOpensActivity {

    private static final String NAME = "Nutella Pie";

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public ActivityTestRule<RecipeStepsActivity> recipeStepsActivityActivityTestRule = new ActivityTestRule<>(RecipeStepsActivity.class);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(activityTestRule.getActivity().getIdlingResource());
    }

    @Test
    public void opensActivity() {
        onView(withText(NAME)).perform(click());

        String name = recipeStepsActivityActivityTestRule.getActivity().getRecipe().getName();
        assertEquals(NAME, name);
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(activityTestRule.getActivity().getIdlingResource());
    }
}
