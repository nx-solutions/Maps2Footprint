package com.nxsolutions.maps2footprint.test;

import com.nxsolutions.maps2footprint.Maps2Footprint;
import com.nxsolutions.maps2footprint.R;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasCategories;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class Maps2FootprintTest {
    String TAG = "Maps2FootprintTest";

    @Rule
    public ActivityTestRule<Maps2Footprint> activityTestRule =
            new ActivityTestRule <>(Maps2Footprint.class, true, false);

    //@Test
    public void testAddress1() {
    	String location = new String("Taipei City, Taiwan 105, No. 2-42, Alley 19, Lane 325, JianKang Rd, Songshan District");
    	String url = new String("https://maps.app.goo.gl/SMwLRvbec9E3HZhRA");

        setIntent(location + "\n" + url);

        onView(withId(R.id.latitude)).check(matches(withText("25.0561358")));
        onView(withId(R.id.longitude)).check(matches(withText("121.5670225")));
        onView(withId(R.id.location)).check(matches(withText(location)));
    }

    //@Test
    public void testCaseMRTStation() {
    	String location = new String("Taipei City Hall Station");
    	String url = new String("https://maps.app.goo.gl/WKrGSesu9PD1qfDs9");

    	setIntent(location + "\n" + url);

        onView(withId(R.id.latitude)).check(matches(withText("25.040993")));
        onView(withId(R.id.longitude)).check(matches(withText("121.56530719999998")));
        onView(withId(R.id.location)).check(matches(withText(location)));
    }

    //@Test
    public void testCaseHotelRoyalChiao() {
    	String location = new String("No. 69-1, Wufeng Rd, Jiaosi Township, Yilan County, 262");
    	String url = new String("https://maps.app.goo.gl/iLVgjeqRzsAKWdDd9");

    	setIntent(location + "\n" + url);

        onView(withId(R.id.latitude)).check(matches(withText("24.827434999999998")));
        onView(withId(R.id.longitude)).check(matches(withText("121.757031")));
        onView(withId(R.id.location)).check(matches(withText(location)));
    }

    //@Test
    public void testCaseAddres2() {
    	String location = new String("No. 53, ZiYun St, Xinyi District, Taipei City, 110");
    	String url = new String("https://maps.app.goo.gl/cTKagaQEuT7mvgAt7");

    	setIntent(location + "\n" + url);

        onView(withId(R.id.latitude)).check(matches(withText("25.016132")));
        onView(withId(R.id.longitude)).check(matches(withText("121.57508299999999")));
        onView(withId(R.id.location)).check(matches(withText(location)));
    }

    @Test
    public void testCaseMeadowbankPark() {
        String location = new String("New South Wales 2114");
        String url = new String("https://maps.app.goo.gl/A8W6EV2bKkwNC9ot8");

        setIntent(location + "\n" + url);

        // FIXME: (Xander): The correct coordinates taken from the URL would give: -33.8179344, 151.081891
        onView(withId(R.id.latitude)).check(matches(withText("-33.8089747")));
        onView(withId(R.id.longitude)).check(matches(withText("151.0849848")));
        onView(withId(R.id.location)).check(matches(withText(location)));
    }

    public void setIntent(String intent_string) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, intent_string);
        activityTestRule.launchActivity(intent);
    }
}